package kz.sdu.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends AbstractBaseEntity{

    @Column(name = "image_file_id")
    private Long imageFileId;
    @Column(name = "title")
    @NonNull
    private String title;
    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    @NonNull
    private LocalDate dateEvent;
    @Column(name = "start_time")
    @NonNull
    private LocalTime timeEvent;
    @Transient
    private Ticket ticket;

    public Event(Long imageFileId, @NonNull String title, String description, boolean isPaid, int countPeople) {
        this.imageFileId = imageFileId;
        this.title = title;
        this.description = description;
        this.ticket = new Ticket(this.getId(), countPeople);
        this.ticket.setPaid(isPaid);
    }

    public Event() {

    }

    public String getInformation() {
        return getTitle() + "\n" +
                getDescription() + "\n" +
                "Payment: " + (getTicket().isPaid() ?
                getTicket().getCost() + " KZT." : "FREE") + "\n" +
                "People: " + getTicket().getCountPeople() + "\n" +
                "Start Time: " + getDateEvent() +
                " " + getTimeEvent();
    }

    public void setTimeEvent(@NonNull LocalDate dateEvent, @NonNull LocalTime timeEvent) {
        setTimeEvent(timeEvent);
        setDateEvent(dateEvent);
    }
}
