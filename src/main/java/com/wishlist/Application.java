package com.wishlist;

import com.wishlist.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class Application  implements CommandLineRunner {

    @Autowired private IUserService userService;

    @Override
    public void run(String... args) throws Exception {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}