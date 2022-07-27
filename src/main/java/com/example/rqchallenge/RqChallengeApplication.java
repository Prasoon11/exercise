package com.example.rqchallenge;

import com.example.rqchallenge.employees.IEmployeeController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = IEmployeeController.class)
public class RqChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RqChallengeApplication.class, args);
    }

}
