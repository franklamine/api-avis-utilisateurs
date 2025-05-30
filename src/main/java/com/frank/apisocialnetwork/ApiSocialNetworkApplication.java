package com.frank.apisocialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //pour exclure les coniguration de spring security
public class ApiSocialNetworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiSocialNetworkApplication.class, args);
    }

}
