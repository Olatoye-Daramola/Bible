package io.olatoye.bible;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BibleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibleApplication.class, args);
    }

}
