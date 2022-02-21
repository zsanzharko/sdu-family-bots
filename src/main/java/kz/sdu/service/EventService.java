package kz.sdu.service;

import kz.sdu.entity.Event;
import kz.sdu.repository.EventRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
@Service
@Slf4j
public final class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void saveEvent(Long idEvent, Long telegram_account_id) {
        // Todo save event to account, using some OneToOne or other.

    }

    public void removeEvent(Long id) {
        eventRepository.deleteById(id);
        log.info("Event with id: {}, has been deleted", id);
    }

    public Event addEvent(Event event) {
        //todo Check data before to save in db, cause in db we have some fields in req not null
        //        return eventRepository.save(event);
        Event db_event = eventRepository.findEventById(event.getId());
        if (db_event != null) {
            return db_event;
        }
        return eventRepository.save(event);
    }

    /**
     * @apiNote
     * Update recent event list will get events when his time is checked in time zone
     * It will do, cause all events does not interested normally student
     */
    public List<Event> getEvents() {
        final ZoneId zoneId = ZoneId.of("Asia/Almaty");

        final LocalDate current_date = LocalDate.now(zoneId);
        final LocalTime current_time = LocalTime.now(zoneId);

        final LocalDate end_date = LocalDate.of(
                current_date.getYear(),
                Month.from(current_date.plusMonths(3L)),
                current_date.getDayOfMonth());

        return eventRepository
                .findAllByDateEventBeforeAndTimeEventBefore(end_date, current_time);
    }

    public Long[] getEventsID(List<Event> events) {
        assert events != null;
        Long[] eventsId = new Long[events.size()];

        for (int i = 0; i < eventsId.length; i++) {
            eventsId[i] = events.get(i).getId();
        }
        return eventsId;
    }

    public List<Event> getEvents(LocalDate endDate) {
        final LocalTime current_time = LocalTime.now();

        return eventRepository
                .findAllByDateEventBeforeAndTimeEventBefore(endDate, current_time);
    }
}
