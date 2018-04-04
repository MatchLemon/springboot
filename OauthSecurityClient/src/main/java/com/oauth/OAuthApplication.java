package com.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SpringBootApplication
public class OAuthApplication {
    @RequestMapping({"/user", "/me"})
    public Principal user(Principal principal) {
        return principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }
}
