package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.EventRepository;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Getter
public class EventBotRepositoryService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final TelegramAccountRepository telegramAccountRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public EventBotRepositoryService(UserRepository userRepository, EventRepository eventRepository, TelegramAccountRepository telegramAccountRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.telegramAccountRepository = telegramAccountRepository;
        this.studentRepository = studentRepository;
    }

    public void save(User user) {
        if (userRepository.findUserByTelegramAccount_TelegramID(user.getTelegramAccount().getTelegramID()) == null)
            userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteByUsername(Long telegramId) {
        userRepository.deleteUserByTelegramAccount_TelegramID(telegramId);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
