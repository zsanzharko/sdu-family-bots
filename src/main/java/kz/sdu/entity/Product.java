package kz.sdu.entity;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product extends Item {

    @ManyToOne
    private User user;

    @Transient
    private File photo;

    @Column(name = "cost")
    private float cost;

    @Column(name = "date_published")
    private LocalDate date_published;

    @Column(name = "time_published")
    private LocalDate time_published;
}
