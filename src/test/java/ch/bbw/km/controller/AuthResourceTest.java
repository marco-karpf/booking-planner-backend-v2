package ch.bbw.km.controller;

import ch.bbw.km.model.User;
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
        User user = new User();
        user.username = "john";
        user.password = "123";
        user.persist();

        given()
                .contentType("application/json")
                .body(user)
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

        given()
                .contentType("application/json")
                .body(user)
                .when().post("/signup")
                .then()
                .statusCode(500);
    }
}
