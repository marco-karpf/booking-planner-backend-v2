package ch.bbw.km.controller;

import ch.bbw.km.model.User;
import ch.bbw.km.security.JwtService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UserResourceTest {

    @Test
    void getUserWithoutJWT() {
        given()
                .contentType("application/json")
                .when().get("/users")
                .then()
                .statusCode(401);
    }

    @Test
    void getUserWithJWT() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/users")
                .then()
                .statusCode(200);
    }

    @Test
    void getUserById() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/users/3865131")
                .then()
                .statusCode(200);
    }

    @Test
    void getNotExistingUserById() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/users/9999999")
                .then()
                .statusCode(404);
    }

    @Test
    void getUsersByUsername() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/users/username/?username=jane")
                .then()
                .statusCode(200);
    }
    @Test
    void getUserByUsernameNotFound() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().get("/users/username/?username=notExisting")
                .then()
                .statusCode(404);
    }

    @Test
    void updateUser() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        User user = new User();
        user.firstName = "Jane";
        user.lastName = "Doe";
        user.age = 67;
        user.username = "jane";
        user.password = "123";
        user.email = "jane.doe@mail.com";
        user.role = "MEMBER";
        user.bookingReason = "Review";
        user.image = "";
        user.profession = "Developer";
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .body(user)
                .when().put("/users/265166")
                .then()
                .statusCode(200);
    }

    @Test
    void updateUserUnsuccessful() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        User user = new User();
        user.firstName = "Jane";
        user.lastName = "Doe";
        user.age = 67;
        user.username = "jane";
        user.password = "123";
        user.role = "MEMBER";
        user.bookingReason = "Review";
        user.image = "";
        user.profession = "Developer";
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .body(user)
                .when().put("/users/265166")
                .then()
                .statusCode(400);
    }

    @Test
    void deleteUser() {
        User admin = User.find("username", "john").firstResult();
        String jwt = new JwtService().generateJwt(admin);
        given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + jwt)
                .when().delete("/users/265166")
                .then()
                .statusCode(200);
    }
}
