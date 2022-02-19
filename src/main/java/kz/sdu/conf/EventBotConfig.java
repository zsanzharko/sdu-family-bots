package kz.sdu.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class EventBotConfig {

    // Имя бота заданное при регистрации
    String botUserName = "sdu_event_bot";

    // Токен полученный при регистрации
    String token = System.getenv("bots:sdu_event_bot");
}
