package kz.sdu.repository;

import kz.sdu.entity.Event;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Event findEventById(Long id);

    Event findEventByTitle(String title);

//    Event findEventByDateEvent(LocalDate dateEvent);

    Event findEventByTimeEvent(LocalTime timeEvent);

    List<Event> findAllByDateEventBetweenAndTimeEventBetween(
            LocalDate dateEvent, LocalDate endDate,
            LocalTime timeEvent, LocalTime endTime);


    List<Event> findAllByDateEventBeforeAndTimeEventBefore(
            @NonNull LocalDate dateEvent,
            @NonNull LocalTime timeEvent);
}
