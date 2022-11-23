package ch.bbw.km.controller;

import ch.bbw.km.model.User;
import ch.bbw.km.security.JwtService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class AuthResourceTest {

    @Test
    @Transactional
    public void invalidLogin() {
        User user = new User();
        user.username = "test";
        user.password = "test";
        user.email = "test@email.com";
        user.role = "Member";
        user.persist();

        given()
                .contentType("application/json")
                .body(user)
                .when().post("/login")
                .then()
                .statusCode(401);
    }

    @Test
    @Transactional
    public void validLogin() {
        User admin = User.find("username", "john").firstResult();

        given()
                .contentType("application/json")
                .body(admin)
                .when().post("/login")
                .then()
                .statusCode(200);
    }

    @Test
    @Transactional
    public void createUser() {
        User user = new User();
        user.firstName = "Max";
        user.lastName = "Mustermann";
        user.age = 20;
        user.username = "max";
        user.password = "123";
        user.email = "max.mustermann@gmail.com";
        user.role = "MEMBER";
        user.image = "";
        user.profession = "tester";
        user.bookingReason = "testing";

        given()
                .contentType("application/json")
                .body(user)
                .when().post("/signup")
                .then()
                .statusCode(201);
    }

    @Test
    @Transactional
    public void createUserWithoutEmail() {
        User user = new User();
        user.firstName = "Max";
        user.lastName = "Mustermann";
        user.age = 20;
        user.username = "max";
        user.password = "123";
        user.role = "MEMBER";
        user.image = "";
        user.profession = "tester";
        user.bookingReason = "testing";

        given()
                .contentType("application/json")
                .body(user)
                .when().post("/signup")
                .then()
                .statusCode(400);
    }

    @Test
    public void logoutUser() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/logout")
                .then()
                .statusCode(200);

    }
    @Test
    public void logoutUserWithoutJWT() {
        given()
                .contentType("application/json")
                .when().get("/logout")
                .then()
                .statusCode(401);
    }
}
