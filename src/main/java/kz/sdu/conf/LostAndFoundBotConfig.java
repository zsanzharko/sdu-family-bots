package kz.sdu.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class LostAndFoundBotConfig {

    private String username = "sdu_lost_and_found_bot";
    private String token = System.getenv("bots:sdu_lost_and_found_bot");
}
