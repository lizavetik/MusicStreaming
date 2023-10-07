package com.tms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusicStreamingApplication {
    static final Logger log = (Logger) LoggerFactory.getLogger(MusicStreamingApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(MusicStreamingApplication.class, args);
    }
}
