package com.frank.apiavisutilisateurs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //pour exclure les coniguration de spring security
public class ApiAvisUtilisateursApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiAvisUtilisateursApplication.class, args);
    }

}
