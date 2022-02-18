package kz.sdu.bot.service;

import kz.sdu.entity.TelegramAccount;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

//fixme Fix classes
@Service
@Getter
public final class AuthorizationTelegramService {

    @Autowired private TelegramAccountRepository accountRepository;
    @Autowired private UserRepository userRepository;

    public TelegramAccount authLogUser(Long id, String chatId) {
        TelegramAccount account = accountRepository.findTelegramAccountModelById(id);
        if (account != null)
            return addAccount(account);
        return addAccount(new TelegramAccount(id, chatId));
    }

    private TelegramAccount addAccount(TelegramAccount account) {
        return accountRepository.save(account);
    }

    public TelegramAccountRepository accountRepository() {
        return accountRepository;
    }

    public UserRepository userRepository() {
        return userRepository;
    }
}
