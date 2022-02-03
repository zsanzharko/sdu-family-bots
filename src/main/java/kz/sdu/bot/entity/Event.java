package kz.sdu.bot.entity;

import lombok.Data;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Data
public class Event {
    //fixme use HashMap
    private static List<Event> recentEvents = new ArrayList<>();
    private static List<Event> eventList = new ArrayList<>();

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

    public static List<Event> getRecentEvents() {
        return recentEvents;
    }

    public static void setRecentEvents(List<Event> recentEvents) {
        Event.recentEvents = recentEvents;
    }

    public static List<Event> getEventList() {
        return eventList;
    }

    public static void setEventList(List<Event> eventList) {
        Event.eventList = eventList;
    }

    /**
     * @apiNote
     * Update recent event list will get events when his time is checked in time zone
     * It will do, cause all events does not interested normally student
     */
    public static void updateRecentEventList() {
        LocalDate localDateNow = LocalDate.now(ZoneId.of("Asia/Almaty"));
        recentEvents.clear();
        for (Event event : getEventList()) {
            //TODO set normal time when it will be start
            if (localDateNow.isBefore(event.getDateEvent())) {
                recentEvents.add(event);
            }
        }
    }
}
