package kz.sdu.service;

import kz.sdu.entity.User;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import java.util.List;

@Service
@Getter
@Setter
public class UserService {

    private UserRepository userRepository;

    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @ToString.Exclude
    private TelegramAccountService telegramAccountModel;

    private EventService eventService; // service that works with events
    private LostItemService lostItemService;

    public UserService(User user) {
        this.user = user;
    }

    public UserService(User user, TelegramAccountService telegramAccountModel, EventService eventService) {
        this.user = user;
        this.telegramAccountModel = telegramAccountModel;
        this.eventService = eventService;
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean setInfoEdit(String command, String text) {
        text = text.trim();
        switch (command) {
            case "/edit_account_name" -> getUser().setName(text);
            case "/edit_account_surname" -> getUser().setSurname(text);
            case "/edit_account_student_id" -> {
                if (User.studentIDChecking(text))
                    getUser().setStudentID(text);
                else return false;
            }
        }
        return true;
    }

    public List<User> findPeople(User user) {
        return userRepository.findAllByStudentID(user.getStudentID());
    }

    public User findUserByTelegramId(Long telegramId) {
        return userRepository.findUserByTelegramId(telegramId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    private void deleteUser(User user) {
        userRepository.delete(user);
    }
}
