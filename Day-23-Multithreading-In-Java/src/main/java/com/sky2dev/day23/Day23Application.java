package com.sky2dev.day23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Day23Application {
    public static void main(String[] args) {
        SpringApplication.run(Day23Application.class, args);
    }
}
