package kz.sdu.entity;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.File;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tickets")
public class Ticket extends AbstractBaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Event event;

    @Transient
    private Document documentTicket;

//    @Column(name = "file_ticket")
    @Transient
    private File fileTicket;

    @Column(name = "cost", nullable = false)
    private float cost; // KZT. 450 tg min cost

    @Column(name = "available_count")
    private int availableCountPeople;

    @Column(name = "total_count")
    private int totalCountPeople;

    public Ticket(int totalCountPeople) {
        this.totalCountPeople = totalCountPeople;
    }

    public Ticket() {

    }
}
