package com.sky2dev.day22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Day22Application {

    public static void main(String[] args) {
        SpringApplication.run(Day22Application.class, args);
    }
}
