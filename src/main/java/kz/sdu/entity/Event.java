package kz.sdu.entity;

import lombok.Data;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Data
public class Event {
    private final Long ID;
    private File image;
    private String title;
    private String description;
    private LocalDate dateEvent;
    private LocalTime timeEvent;
    private Ticket ticket;

    public Event(File image, String title, String description, boolean paid, int countPeople) {
        this.ID = (long)(Math.random() * Long.MAX_VALUE);
        this.image = image;
        this.title = title;
        this.description = description;
        this.ticket = new Ticket(this.getID(), countPeople);
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
