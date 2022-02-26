package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Getter
@Slf4j
public class MarketPlaceBotService extends AbstractTelegramBotService {

    private final UserService userService;
    private final ProductService productService;

    public MarketPlaceBotService(UserService userService, ProductService productService) {
        super(new AuthorizationTelegramService(userService));
        this.userService = userService;
        this.productService = productService;
    }
}
