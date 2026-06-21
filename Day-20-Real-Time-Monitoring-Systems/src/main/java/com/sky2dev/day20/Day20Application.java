package com.sky2dev.day20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Day20Application {

    public static void main(String[] args) {
        SpringApplication.run(Day20Application.class, args);
    }
}
