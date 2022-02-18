package kz.sdu.conf;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ChallengeBotConfig {

    private String username = "sdu_challenge_bot";

    private String token = System.getenv("bots:sdu_challenge_bot");
}
