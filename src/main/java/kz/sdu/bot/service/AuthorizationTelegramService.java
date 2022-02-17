package kz.sdu.bot.service;

import kz.sdu.entity.TelegramAccount;
import kz.sdu.repository.TelegramAccountRepository;
import kz.sdu.repository.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

//fixme Fix classes
@Service
@Getter
public record AuthorizationTelegramService(TelegramAccountRepository accountRepository,
                                           UserRepository userRepository) {

    public TelegramAccount authLogUser(Long id, String chatId) {
        TelegramAccount account = accountRepository.findTelegramAccountModelById(id);
        if (account != null)
            return addAccount(account);
        return addAccount(new TelegramAccount(id, chatId));
    }

    private TelegramAccount addAccount(TelegramAccount account) {
        return accountRepository.save(account);
    }
}
