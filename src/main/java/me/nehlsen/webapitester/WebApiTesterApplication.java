package me.nehlsen.webapitester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebApiTesterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApiTesterApplication.class, args);
    }

}
