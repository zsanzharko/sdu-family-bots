package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TicketRepository;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class EventBotRepositoryService {

    //services
    @Getter
    private final AuthorizationTelegramService authTelegramService;
    @Getter
    private final EventService eventService;
    @Getter
    private final TicketService ticketService;
    @Getter
    private final UserService userService;

    @Autowired
    public EventBotRepositoryService(EventRepository eventRepository,
            TicketRepository ticketRepository, UserService userService) {
        //repositories
        this.userService = userService;

        //services
        this.authTelegramService = new AuthorizationTelegramService(userService);
        this.eventService = new EventService(eventRepository);
        this.ticketService = new TicketService(ticketRepository);
    }

    public User authUser(@NonNull Update update) {
        return authTelegramService.authLogUser(update);
    }

}
