package kz.sdu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class SduBotsFamilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SduBotsFamilyApplication.class, args);
//        new SpringApplicationBuilder(SduBotsFamilyApplication.class)
//                .run(args);
    }
}


