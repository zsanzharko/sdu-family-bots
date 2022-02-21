package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        User db_user = userRepository.findUserByTelegramAccount_TelegramID(user.getTelegramAccount().getTelegramID());
        if (db_user == null) {
            return userRepository.save(user);
        }
        return user;
    }

    public void updateAndSave(User user) {
        User db_user = userRepository.findUserByTelegramAccount_TelegramID(user.getTelegramAccount().getTelegramID());
        if (user == db_user) {
            return;
        }
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
