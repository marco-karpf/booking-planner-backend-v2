package ch.bbw.km.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BookingDate extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public LocalDateTime startDateTime;
    @Column(nullable = false)
    public LocalDateTime endDateTime;
}
