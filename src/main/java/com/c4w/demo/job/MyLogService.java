package com.c4w.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
@Slf4j
public class MyLogService {

    public void log(String message) {
        log.warn("{} at {}", message, LocalTime.now().format(ofPattern("hh:mm:ss")));
    }
}
