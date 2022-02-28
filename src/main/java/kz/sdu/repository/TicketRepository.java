package kz.sdu.repository;

import kz.sdu.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findTicketById(Long id);

    Ticket findTicketByCostLessThan(float cost);

    List<Ticket> findByAvailableCountPeople(int availableCountPeople);
}
