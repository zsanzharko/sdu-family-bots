package kz.sdu.repository;

import kz.sdu.entity.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventRepository extends CrudRepository<Event, Long> {
    List<Event> findEventByIDAndIDAfter(Long id);
}
