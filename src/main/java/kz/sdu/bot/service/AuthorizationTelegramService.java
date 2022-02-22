package kz.sdu.bot.service;

import kz.sdu.entity.Student;
import kz.sdu.entity.TelegramAccount;
import kz.sdu.entity.User;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

//fixme Fix classes
@Service
@Getter
@Slf4j
public final class AuthorizationTelegramService {

    private final TelegramAccountRepository accountRepository;
    private final UserRepository userRepository;

    public AuthorizationTelegramService(TelegramAccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public User authLogUser(Update update) {
        final String telegram_username = update.getMessage().getChat().getUserName() == null ?
                "User" : update.getMessage().getChat().getUserName();
        final Long telegram_id = update.getMessage().getFrom().getId();
        final String telegram_chat_id = String.valueOf(update.getMessage().getChatId());
        final String name = update.getMessage().getChat().getFirstName() == null ?
                "" : update.getMessage().getChat().getFirstName();
        final String surname = update.getMessage().getChat().getLastName() == null ?
                "" : update.getMessage().getChat().getLastName();
        final String bio = update.getMessage().getChat().getBio() == null ?
                "" : update.getMessage().getChat().getBio();

        try {
            User account = userRepository.findUserByTelegramAccount_TelegramId(telegram_id);
            if (account != null) return account;
        } catch (Exception e) {
            e.printStackTrace();
        }

        TelegramAccount telegramAccount = new TelegramAccount(telegram_id, telegram_chat_id, telegram_username);
        Student student = new Student();
        User user = new User(telegramAccount, name, surname);

        user.setStudent(student);
        user.setBio(bio);

        return addAccount(user);
    }

    private TelegramAccount addAccount(TelegramAccount account) {
        return accountRepository.save(account);
    }

    private User addAccount(User account) {
        return userRepository.save(account);
    }

    public TelegramAccountRepository accountRepository() {
        return accountRepository;
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
