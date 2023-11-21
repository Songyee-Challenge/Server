package com.likelion.songyeechallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SongyeeChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SongyeeChallengeApplication.class, args);
    }

}
