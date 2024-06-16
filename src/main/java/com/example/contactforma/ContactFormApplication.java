package com.example.contactforma;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@ComponentScan("com.example.contactform.controller")
@ComponentScan("com.example.contactform.model")
@ComponentScan("com.example.contactform.repository.ContactRepository")
@ComponentScan("com.example.contactform.service")*/

public class ContactFormApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactFormApplication.class, args);
    }
}
