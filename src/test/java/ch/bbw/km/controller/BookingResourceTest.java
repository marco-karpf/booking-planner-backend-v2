package ch.bbw.km.controller;

import ch.bbw.km.model.Booking;
import ch.bbw.km.model.BookingDate;
import ch.bbw.km.model.Status;
import ch.bbw.km.model.User;
import ch.bbw.km.security.JwtService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;

@QuarkusTest
class BookingResourceTest {

    @Test
    void getAllBookings() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings")
                .then()
                .statusCode(200);
    }

    @Test
    void getAllBookingsWithoutJWT() {
        given()
                .contentType("application/json")
                .when().get("/bookings")
                .then()
                .statusCode(401);
    }
    @Test
    void getAllBookingsWithoutRequiredPermission() {
        User member = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(member);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings")
                .then()
                .statusCode(403);
    }

    @Test
    void getBookingById() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings/1213")
                .then()
                .statusCode(200);
    }

    @Test
    void getNonExistingBookingById() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings/999999999")
                .then()
                .statusCode(404);
    }

    @Test
    void getBookingByUserUsernameAndTitle() {
        User member = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(member);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings/user/jane?title=Review")
                .then()
                .statusCode(200);
    }
    @Test
    void getBookingByUserUsernameAndTitleForAnotherUserWithoutJWT() {
        User member = User.find("username", "jane").firstResult();
        given()
                .contentType("application/json")
                .when().get("/bookings/user/john?title=Review")
                .then()
                .statusCode(401);
    }

    @Test
    void getBookingByStatus() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/bookings/status/pending")
                .then()
                .statusCode(200);
    }

    @Test
    void createBooking() {
        User member = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(member);
        BookingDate testBookingDate = new BookingDate();
        testBookingDate.startDateTime = LocalDateTime.now();
        testBookingDate.endDateTime = LocalDateTime.now().plusHours(1);
        Booking booking = new Booking();
        booking.title = "Review";
        booking.status = Status.findById(1L);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .body(booking)
                .when().post("/bookings")
                .then()
                .statusCode(201);
    }

    @Test
    void updateBooking() {
        User user = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(user);
        Booking booking = Booking.findById(1213L);
        booking.title = "Review Test";
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .body(booking)
                .when().put("/bookings/1213")
                .then()
                .statusCode(200);
    }

    @Test
    void cancelBooking() {
        User user = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(user);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().put("/bookings/cancel/56765")
                .then()
                .statusCode(200);
    }

    @Test
    void confirmBooking() {
        User user = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(user);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().put("/bookings/confirm/43533454")
                .then()
                .statusCode(200);
    }

    @Test
    void deleteBookingWithoutRequiredPermission() {
        User user = User.find("username", "jane").firstResult();
        String jwt = new JwtService().generateJwt(user);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().delete("/bookings/1213")
                .then()
                .statusCode(403);
    }

    @Test
    void deleteBookingWithRequiredPermission() {
        User user = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(user);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().delete("/bookings/1213")
                .then()
                .statusCode(204);
    }


}
