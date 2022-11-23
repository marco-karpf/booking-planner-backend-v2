package ch.bbw.km.controller;

import ch.bbw.km.model.Booking;
import ch.bbw.km.model.User;
import ch.bbw.km.service.BookingService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/bookings")
public class BookingResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    BookingService bookingService;


    /**
     * Get all bookings
     *
     * @return List of all bookings
     */
    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all bookings", description = "Get all bookings")
    public Response getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return Response.ok(bookings).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Get a booking by id
     *
     * @return the booking with the given id
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MEMBER"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a booking by id", description = "Get a booking by id")
    public Response getBookingById(@PathParam("id") Long id) {

        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return Response.ok(booking).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * get all bookings of for admin and all bookings of the logged-in user for members.
     * It is also possible to search by title
     *
     * @param username username of the user to get the bookings for
     * @param title    title of  bookings to get
     * @return list of bookings
     */
    @GET
    @Path("/user/{username}")
    @RolesAllowed({"MEMBER", "ADMIN"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all bookings of a user and optional by title", description = "Get all bookings of a user and optional by title")
    public Response getBookingByUserUsernameAndTitle(@PathParam("username") String username, @QueryParam("title") String title) {
        User user = User.find("email", jwt.getClaim("email").toString()).singleResult();
        List<Booking> bookings;
        if (user.username.equals(username)) {
            if (user.role.equals("ADMIN") && title != null) {
                bookings = bookingService.getAllBookings();
                bookings.removeIf(booking -> !booking.title.toLowerCase().contains(title.toLowerCase()));
                return Response.ok(bookings).build();
            } else if (user.role.equals("ADMIN") && title == null) {
                bookings = bookingService.getAllBookings();
                return Response.ok(bookings).build();
            } else if (user.role.equals("MEMBER") && title != null) {
                bookings = bookingService.getBookingsByTitleAndUsername(title, username);
                return Response.ok(bookings).build();
            } else if (user.role.equals("MEMBER")) {
                bookings = bookingService.getBookingsByUsername(username);
                return Response.ok(bookings).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * find bookings by status
     *
     * @param status String status of the booking
     * @return list of bookings
     */
    @GET
    @Path("/status/{status}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all bookings by status", description = "Get all bookings by status")
    public Response getBookingByStatus(@PathParam("status") String status) {
        try {
            List<Booking> bookings = bookingService.getBookingsByStatus(status);
            return Response.ok(bookings).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Creates a new booking for the logged in user
     *
     * @param booking the booking to create
     * @return the created response
     */
    @POST
    @RolesAllowed({"ADMIN", "MEMBER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new booking", description = "Create a new booking")
    @Transactional
    public Response createBooking(Booking booking) {
        User user = User.find("email", jwt.getClaim("email").toString()).singleResult();
        try {
            bookingService.createBooking(booking);
            user.bookings.add(booking);
            user.persist();
            return Response.created(URI.create("/booking")).build();
        } catch (Exception e) {
            System.out.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Updates a booking
     *
     * @param id      id of the booking to update
     * @param booking the booking to update
     * @return the updated booking
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "MEMBER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a booking", description = "Update a booking")
    public Response updateBooking(@PathParam("id") Long id, Booking booking) {
        Booking bookingToUpdate = bookingService.getBookingById(id);
        if (bookingToUpdate != null) {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            return Response.ok(updatedBooking).build();

        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * sets the status of the booking with the given id to cancelled as admin and to requested for cancellation for members
     *
     * @param id id of the booking to delete
     * @return the updated booking with the updated status
     */
    @PUT
    @Path("/cancel/{id}")
    @RolesAllowed({"ADMIN", "MEMBER"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Cancel a booking", description = "Cancel a booking or sending request for cancellation")
    public Response cancelBooking(@PathParam("id") Long id) {
        User user = User.find("email", jwt.getClaim("email").toString()).singleResult();
        Booking bookingToUpdate = bookingService.cancelBooking(user, id);
        if (bookingToUpdate != null) {
            return Response.ok(bookingToUpdate).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * sets the status of the booking with the given id to approved as admin
     *
     * @param id id of the booking to update
     * @return the updated booking
     */
    @PUT
    @Path("/confirm/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Confirm a booking", description = "Confirm a booking")
    public Response confirmBooking(@PathParam("id") Long id) {
        Booking bookingToUpdate = bookingService.confirmBooking(id);
        if (bookingToUpdate != null) {
            return Response.ok(bookingToUpdate).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * deletes a booking
     *
     * @param id id of the booking to delete
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a booking", description = "Delete a booking")
    public Response deleteBooking(@PathParam("id") Long id) {
        if (Booking.findById(id) != null) {
            bookingService.deleteBooking(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
