package kz.sdu.service;

import kz.sdu.bot.service.AuthorizationTelegramService;
import kz.sdu.entity.Product;
import kz.sdu.entity.User;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Getter
@Slf4j
public class MarketPlaceBotService extends AbstractTelegramBotService {

    private static final String LOGTAG = "MARKET-PLACE-BOT-SERVICE";

    private final UserService userService;
    private final ProductService productService;

    public MarketPlaceBotService(UserService userService, ProductService productService) {
        super(new AuthorizationTelegramService(userService));
        this.userService = userService;
        this.productService = productService;
    }

    public Product saveProduct(Product product, User user) {
        if (user == null && product == null) return null;
        if (Objects.requireNonNull(user).getSellingProduct() == null) return null;

        user.getSellingProduct().add(product);

        try {
            userService.save(user);
            return product;
        } catch (Exception e) {
            log.error(LOGTAG, e);
            log.error("Doesn't saved user with product");
            log.info(getInformationAboutUserProduct(product, user));
        }
        return null;
    }

    public void removeProduct(Product product, User user) {
        if (user == null && product == null) return;
        if (Objects.requireNonNull(user).getSellingProduct() == null) return;

        try {
            //todo check this moment with removing product in user
            productService.remove(product);
        } catch (Exception e) {
            log.error(LOGTAG, e);
            log.error("Doesn't removed user with product");
            log.info(getInformationAboutUserProduct(product, user));
        }
    }

    private String getInformationAboutUserProduct(Product product, User user) {
        return String.format("User: {telegram id: %d, chat id: %s}, Product: { id: %d, name: %s}",
                user.getTelegramAccount().getTelegramId(),
                user.getTelegramAccount().getChatId(),
                product.getId(),
                product.getName());
    }
}
