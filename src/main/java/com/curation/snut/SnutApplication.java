package com.curation.snut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SnutApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnutApplication.class, args);
    }

}
