package ch.bbw.km.service;

import ch.bbw.km.model.Booking;
import ch.bbw.km.model.BookingDate;
import ch.bbw.km.model.Status;
import ch.bbw.km.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class BookingService {

    public List<Booking> getAllBookings() {
        return Booking.listAll();
    }

    public Booking getBookingById(Long id) {
        return Booking.findById(id);
    }

    public List<Booking> getBookingsByTitleAndUsername(String title, String username) {
        List<Booking> bookings = Booking.list("SELECT b.bookings FROM User b WHERE b.username = ?1", username);
        bookings.removeIf(booking -> !booking.title.toLowerCase().contains(title.toLowerCase()));
        return bookings;
    }

    public List<Booking> getBookingsByUsername(String username) {
        return Booking.list("SELECT b.bookings FROM User b WHERE b.username = ?1", username);
    }

    public List<Booking> getBookingsByStatus(String status) {
        System.out.println(status);
        return Booking.list("SELECT b FROM Booking b WHERE b.status.status = ?1", status);
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        booking.status = Status.findById(1L);
        booking.persist();
        return booking;
    }

    @Transactional
    public Booking updateBooking(Long id, Booking booking) {
        Booking bookingToUpdate = getBookingById(id);
        if (bookingToUpdate != null) {
            for (BookingDate bookingDate : booking.bookingDates) {
                BookingDate bookingDateToUpdate = BookingDate.findById(bookingDate.id);
                bookingDateToUpdate.startDateTime = bookingDate.startDateTime;
                bookingDateToUpdate.endDateTime = bookingDate.endDateTime;
                bookingDateToUpdate.persist();
            }
            bookingToUpdate.title = booking.title;
            bookingToUpdate.status = booking.status;
            bookingToUpdate.persist();
            return bookingToUpdate;
        }
        return null;
    }

    @Transactional
    public Booking cancelBooking(User user, Long id) {
        Booking bookingToUpdate = getBookingById(id);
        if (bookingToUpdate != null) {
            if (user.role.equals("ADMIN")) {
                bookingToUpdate.status = Status.find("status", "cancelled").singleResult();
                bookingToUpdate.persist();
                return bookingToUpdate;
            } else if (user.role.equals("MEMBER")) {
                bookingToUpdate.status = Status.find("status", "requested for cancellation").singleResult();
                bookingToUpdate.persist();
                return bookingToUpdate;
            }
        }
        return null;
    }

    @Transactional
    public Booking confirmBooking(Long id) {
        Booking bookingToUpdate = getBookingById(id);
        bookingToUpdate.status = Status.find("status", "approved").singleResult();
        bookingToUpdate.persist();
        return bookingToUpdate;
    }

    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        booking.delete();
    }

}
