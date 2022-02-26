package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.entity.User;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public abstract class AbstractTelegramBotService {

    @Getter
    private final AuthorizationTelegramService authTelegramService;

    protected AbstractTelegramBotService(AuthorizationTelegramService authTelegramService) {
        this.authTelegramService = authTelegramService;
    }

    public User authUser(@NonNull Update update) {
        return authTelegramService.authLogUser(update);
    }
}
