package kz.sdu.bot.service;

import kz.sdu.entity.TelegramAccount;
import kz.sdu.entity.User;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

//fixme Fix classes
@Service
@Getter
public final class AuthorizationTelegramService {

    private final TelegramAccountRepository accountRepository;
    private final UserRepository userRepository;

    public AuthorizationTelegramService(TelegramAccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public User authLogUser(Long id, String chatId) {
        User account = userRepository.findUserByTelegramAccount_TelegramID(accountRepository.findTelegramAccountByIdAndChatId(id, chatId).getId());
        if (account != null)
            return addAccount(account);
        return addAccount(new User(new TelegramAccount(id, chatId)));
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
