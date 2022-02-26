package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TicketRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventBotRepositoryService extends AbstractTelegramBotService {

    //services
    @Getter
    private final EventService eventService;
    @Getter
    private final TicketService ticketService;
    @Getter
    private final UserService userService;

    @Autowired
    public EventBotRepositoryService(EventRepository eventRepository,
            TicketRepository ticketRepository, UserService userService) {
        super(new AuthorizationTelegramService(userService));
        //repositories
        this.userService = userService;

        //services
        this.eventService = new EventService(eventRepository);
        this.ticketService = new TicketService(ticketRepository);
    }

}
