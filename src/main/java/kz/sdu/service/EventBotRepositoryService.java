package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EventBotRepositoryService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TelegramAccountRepository telegramAccountRepository;
    private final StudentRepository studentRepository;

    private final AuthorizationTelegramService authTelegramService;
    private final EventService eventService;

    @Autowired
    public EventBotRepositoryService(
            UserRepository userRepository, EventRepository eventRepository,
            TelegramAccountRepository telegramAccountRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.telegramAccountRepository = telegramAccountRepository;
        this.studentRepository = studentRepository;
        //services
        this.authTelegramService = new AuthorizationTelegramService(telegramAccountRepository, userRepository);
        this.eventService = new EventService(eventRepository);
    }

    public User authUser(@NonNull Long telegramAccountId, @NonNull String chatId) {
        return authTelegramService.authLogUser(telegramAccountId, chatId);
    }

    public void saveUser(User user) {
        if (userRepository.findUserByTelegramAccount_TelegramID(user.getTelegramAccount().getTelegramID()) == null)
            userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteByUsername(Long telegramId) {
        userRepository.deleteUserByTelegramAccount_TelegramID(telegramId);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
