package ch.bbw.km.controller;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class BookingResourceTest {

//    @Test
//    void getAllBookings() {
//        User admin = User.find("username", "john").firstResult();
//        String jwt = new JwtService().generateJwt(admin);
//        given()
//                .contentType("application/json")
//                .header("Authorization", "Bearer " + jwt)
//                .when().get("/bookings")
//                .then()
//                .statusCode(200);
//
//    }

    @Test
    void getAllBookingsWithoutJWT() {
        given()
                .contentType("application/json")
                .when().get("/bookings")
                .then()
                .statusCode(401);
    }

    @Test
    void getBookingById() {
    }

    @Test
    void getBookingByUserUsernameAndTitle() {
    }

    @Test
    void createBooking() {
    }

    @Test
    void updateBooking() {
    }

    @Test
    void cancelBooking() {
    }

    @Test
    void confirmBooking() {
    }

    @Test
    void deleteBooking() {
    }
}
