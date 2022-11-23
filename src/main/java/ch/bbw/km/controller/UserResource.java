package ch.bbw.km.controller;

import ch.bbw.km.model.User;
import ch.bbw.km.service.UserService;
import org.eclipse.microprofile.openapi.annotations.Operation;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
public class UserResource {


    @Inject
    UserService userService;

    /**
     * Get all users
     *
     * @return List of all users
     */
    @GET
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all users", description = "Get all users")
    public Response getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * Get a user by id
     *
     * @return the user with the given id
     */
    @GET
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a user by id", description = "Get a user by id")
    public Response getUserById(@PathParam("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * gets a list of users search by username
     *
     * @param username the username to search for
     * @return a list of users with the given username
     */
    @GET
    @Path("/username")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Get a list of users by username", description = "Get a list of users by username")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByUsername(@QueryParam("username") String username) {
        try {
            List<User> users = userService.getUserByUsername(username);
            if (users.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            return Response.ok(users).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    /**
     * updates a user
     *
     * @param user the user to update
     * @return an updated user
     */
    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a user", description = "Update a user")
    public Response updateUser(@PathParam("id") Long id, User user) {
        User userToUpdate = userService.getUserById(id);
        if (userToUpdate != null) {
            try {
            User updatedUser = userService.updateUser(id, user);
            return Response.ok(updatedUser).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    /**
     * deletes a user
     *
     * @param id the id of the user to delete
     * @return a response
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a user", description = "Delete a user")
    public Response deleteUser(@PathParam("id") Long id) {
        User userToDelete = userService.getUserById(id);
        if (userToDelete != null) {
            userService.deleteUser(id);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
