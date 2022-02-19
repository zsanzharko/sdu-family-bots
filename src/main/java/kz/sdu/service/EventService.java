package kz.sdu.service;

import kz.sdu.entity.Event;
import kz.sdu.repository.EventRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@Service
public final class EventService {
//    private final EventRepository eventRepository;

    @Autowired
    public EventService(/*EventRepository eventRepository*/) {
//        this.eventRepository = eventRepository;
    }

    public void removeEvent(Long id) {
        //todo Check event before to delete
//        eventRepository.deleteById(id);
    }

    public Event addEvent(Event event) {
        //todo Check data before to save in db, cause in db we have some fields in req not null
        //        return eventRepository.save(event);
        return null;
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
