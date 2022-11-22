package ch.bbw.km.security;

import ch.bbw.km.model.User;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import javax.inject.Singleton;

@Singleton
public class JwtService {

    public String generateJwt(User user) {
        return Jwt.issuer("zurich-crowdfunding-space")
                .upn(user.username)
                .subject("zurich-crowdfunding-space")
                .groups(user.role)
                .expiresIn(86400)
                .claim(Claims.email.name(), user.email)
                .sign();
    }
}
