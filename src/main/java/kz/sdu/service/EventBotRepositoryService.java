package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.TicketRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public EventBotRepositoryService(
            UserRepository userRepository, EventRepository eventRepository,
            TelegramAccountRepository telegramAccountRepository, StudentRepository studentRepository, TicketRepository ticketRepository, UserService userService) {
        //repositories
        //repositories
        this.userService = userService;
        //services
        this.authTelegramService = new AuthorizationTelegramService(telegramAccountRepository, userRepository);
        this.eventService = new EventService(eventRepository);
        this.ticketService = new TicketService(ticketRepository);
    }

    public User authUser(@NonNull Long telegramAccountId, @NonNull String chatId) {
        return authTelegramService.authLogUser(telegramAccountId, chatId);
    }

}
