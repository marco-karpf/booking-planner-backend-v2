package ch.bbw.km.model;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.*;
import java.util.List;

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
    @Column(nullable = false)
    @Username
    public String username;
    @Column(nullable = false)
    @Password
    public String password;
    @Column(length = 50, unique = true, nullable = false)
    public String email;
    @Roles
    @Column(nullable = false)
    public String role;
    public String image;
    public String profession;
    public String bookingReason;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public List<Booking> bookings;

}
