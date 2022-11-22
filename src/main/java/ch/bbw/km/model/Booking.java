package ch.bbw.km.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
public class Booking extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public String title;
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    public Status status;
    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    public List<BookingDate> bookingDates;

}
