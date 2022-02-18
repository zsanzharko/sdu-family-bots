package kz.sdu.sdubotsfamily;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan("kz")
@EntityScan("src.main.java.kz.sdu.entity")
@EnableJpaRepositories("kz.sdu.repository")
public class SduBotsFamilyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SduBotsFamilyApplication.class, args);
//        new SpringApplicationBuilder(SduBotsFamilyApplication.class)
//                .run(args);
    }
}


