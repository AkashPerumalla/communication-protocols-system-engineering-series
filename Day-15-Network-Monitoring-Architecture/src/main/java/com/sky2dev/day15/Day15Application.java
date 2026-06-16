package com.sky2dev.day15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Day15Application {

    public static void main(String[] args) {
        SpringApplication.run(Day15Application.class, args);
    }
}
