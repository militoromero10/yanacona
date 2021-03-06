package com.yanacona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class YanaconaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YanaconaApplication.class, args);
    }

}
