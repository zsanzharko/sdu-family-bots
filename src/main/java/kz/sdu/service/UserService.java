package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        User db_user = userRepository.findUserByTelegramAccount_TelegramId(user.getTelegramAccount().getTelegramId());
        if (db_user == null) {
            return userRepository.save(user);
        }
        return user;
    }

    public String getInformationAccountToTelegram(Long telegram_id) {
        User user = userRepository.findUserByTelegramAccount_TelegramId(telegram_id);
        if (user == null) return "User doesn't have in database, please authorize";

        return "Name: " + user.getName() + "\n" +
                "Surname: " + user.getSurname() + "\n" +
                "student ID: " + user.getStudent().getStudentID() + "\n" +
                "Liked Event: " + user.getFavoriteEvents().size();

    }

    public void updateAndSave(User user) {
        User db_user = userRepository.findUserByTelegramAccount_TelegramId(user.getTelegramAccount().getTelegramId());
        if (user == db_user) {
            return;
        }
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void deleteByUsername(Long telegramId) {
        userRepository.deleteUserByTelegramAccount_TelegramId(telegramId);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
