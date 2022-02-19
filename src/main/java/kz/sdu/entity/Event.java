package kz.sdu.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends AbstractBaseEntity{
    @Transient
    private File image;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate dateEvent;
    @Column(name = "start_time")
    private LocalTime timeEvent;
    @Transient
    private Ticket ticket;

    public Event(File image, String title, String description, boolean paid, int countPeople) {
        this.id = (long)(Math.random() * Long.MAX_VALUE);
        this.image = image;
        this.title = title;
        this.description = description;
        this.ticket = new Ticket(this.getId(), countPeople);
    }

    public Event() {

    }

    public String getInformation() {
        return getTitle() + "\n" +
                getDescription() + "\n" +
                "Payment: " + (getTicket().isPaid() ?
                getTicket().getCost() + " KZT." : "FREE") + "\n" +
                "People: " + getTicket().getCountPeople() + "\n" +
                "Start Time: " + getDateEvent().toString() +
                " " + getTimeEvent().toString();
    }

    public void setTimeEvent(LocalDate dateEvent, LocalTime timeEvent) {
        setTimeEvent(timeEvent);
        setDateEvent(dateEvent);
    }
}
