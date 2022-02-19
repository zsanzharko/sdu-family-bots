package kz.sdu.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class MarketPlaceBotConfig {

    private String username = "sdu_market_place_bot";
    private String token = System.getenv("bots:sdu_market_place_bot");
}
