package ch.bbw.km.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;

/**
 * Class: Status
 * @author marco
 * @version 22.11.22
 */
@Entity
public class Status extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column(nullable = false)
    public String status;
}
