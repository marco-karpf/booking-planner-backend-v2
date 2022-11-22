package ch.bbw.km.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Class: User
 * @author marco
 * @version 22.11.22
 */
@UserDefinition
@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String lastName;
    public String firstName;
    public int age;
    @NotBlank(message = "Username is mandatory")
    @Username
    public String username;
    @NotBlank(message = "Password is mandatory")
    @Password
    public String password;
    @Column(length = 50, unique = true)
    @NotBlank(message = "Email is mandatory")
    public String email;
    @Roles
    @NotBlank(message = "Role is mandatory")
    public String role;
    public String image;
    public String profession;
    public String bookingReason;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public List<Booking> bookings;

}
