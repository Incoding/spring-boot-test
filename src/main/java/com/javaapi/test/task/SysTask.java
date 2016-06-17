package com.javaapi.test.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SysTask {
    @Scheduled(fixedRate = 5000)
    public void test() {
        System.out.println("true = " + LocalDateTime.now());
    }
}
