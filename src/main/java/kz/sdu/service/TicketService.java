package kz.sdu.service;

import kz.sdu.entity.Ticket;
import kz.sdu.repository.TicketRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
public class TicketService {

    private final TicketRepository ticketRepository;

    private Ticket ticket;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void paying(Ticket ticket, float cost) {
        setCost(ticket, cost);
    }

    private void setCost(Ticket ticket, float cost) {
        if (cost > 450.0f && cost < 4_369_647.00f)
            ticket.setCost(cost);
        else ticket.setCost(0);
    }
}
