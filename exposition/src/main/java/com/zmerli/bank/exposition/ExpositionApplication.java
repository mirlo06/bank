package com.zmerli.bank.exposition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.zmerli.bank.*")
public class ExpositionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpositionApplication.class, args);
    }

}
