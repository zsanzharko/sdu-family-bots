package kz.sdu.service;

import kz.sdu.entity.Event;
import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
@Service
public record EventService(
        List<Event> events,
        List<Event> favoriteEvent,
        EventRepository eventRepository) {

    public void removeEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addFavoriteEvent(Long ID) {
        Event event = Event.getRecentEvents().get(binary_search(Event.getRecentEvents(), ID));
        favoriteEvent.add(event);
    }

    public void removeFavoriteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    /**
     * @apiNote
     * Update recent event list will get events when his time is checked in time zone
     * It will do, cause all events does not interested normally student
     */
    public static void updateRecentEventList() {
        LocalDate localDateNow = LocalDate.now(ZoneId.of("Asia/Almaty"));

    }
}
