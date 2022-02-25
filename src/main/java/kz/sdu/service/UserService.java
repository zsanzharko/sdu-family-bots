package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.StudentRepository;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final TelegramAccountRepository telegramAccountRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public UserService(UserRepository userRepository, TelegramAccountRepository telegramAccountRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.telegramAccountRepository = telegramAccountRepository;
        this.studentRepository = studentRepository;
    }

    public User save(User user) {
        User db_user = userRepository.findUserByTelegramAccount_TelegramId(user.getTelegramAccount().getTelegramId());
        if (db_user == null) {
            telegramAccountRepository.save(user.getTelegramAccount());
            studentRepository.save(user.getStudent());
            return userRepository.save(user);
        }
        return user;
    }

    public User findUserByTelegramId(Long telegramId) {
        return userRepository.findUserByTelegramAccount_TelegramId(telegramId);
    }

    public String getInformationAccountToTelegram(Long telegram_id) {
        User user = userRepository.findUserByTelegramAccount_TelegramId(telegram_id);
        if (user == null) return "User doesn't have in database, please authorize";

        String studentId = "don't set";
        int likedEventsSize = 0;
        if (user.getStudent() != null)
         studentId = user.getStudent().getStudentID();
        if (user.getFavoriteEvents() != null)
            likedEventsSize = user.getFavoriteEvents().size();

        return "Name: " + user.getName() + "\n" +
                "Surname: " + user.getSurname() + "\n" +
                "student ID: " + studentId + "\n" +
                "Liked Event: " + likedEventsSize;

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
