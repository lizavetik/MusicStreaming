package com.tms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Music streaming application project",
                description = "This is pet project",
                contact = @Contact(
                        name = "Kovaleva Liza",
                        email = "fiz.kovalevaee@bsu.by",
                        url = "@kovalevaliz"
                )
        )
)
@SpringBootApplication
public class MusicStreamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicStreamingApplication.class, args);
    }
}
