package ch.bbw.km.controller;

import ch.bbw.km.model.User;
import ch.bbw.km.security.JwtService;
import ch.bbw.km.service.UserService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class AuthResource {

    @Inject
    JwtService jwtService;
    @Inject
    UserService userService;
    @Inject
    JsonWebToken jwt;

    /**
     * creates a user, sets the first user to admin and the rest to member
     *
     * @param user the user to create
     * @return the created response
     */
    @POST
    @Transactional
    @Path("/signup")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a user", description = "Create a user")
    public Response createUser(@Valid User user) {
        if (User.count() == 0) {
            user.role = "ADMIN";
        } else {
            user.role = "MEMBER";
        }
        user = userService.createUser(user);
        if (user.isPersistent()) {
            return Response.created(URI.create("/user" + user.id)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * checks if the user exists and if username and password match, then creates a jwt token
     *
     * @param user the user to login
     * @return jwt token and the user
     */
    @POST
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Login a user", description = "Login a user")
    public Response login(User user) {
        try {
            User userToLogin = User.find("username", user.username).singleResult();
            if (userToLogin != null && userToLogin.password.equals(user.password)) {
                String jwt = jwtService.generateJwt(userToLogin);
                JSONObject locaStorage = new JSONObject();
                locaStorage.put("jwt", jwt);
                locaStorage.put("user", userToLogin);
                return Response.ok(locaStorage).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    /**
     * returns message that the user is logged out
     *
     * @return message that the user is logged out
     */
    @GET
    @Path("/logout")
    @RolesAllowed({"ADMIN", "MEMBER"})
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Logout a user", description = "Logout a user")
    public Response logout() {
        User user = User.find("email", jwt.getClaim("email").toString()).singleResult();
        if (user != null) {
            return Response.ok("User " + user.username + " logged out").build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
