package com.wishlist;

import com.wishlist.model.User;
import com.wishlist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        this.repository.deleteAll();

        // save a couple of customers
        this.repository.save(new User("Alice", "Smith"));
        this.repository.save(new User("Bob", "Smith"));
        this.repository.save(new User("Vova", "Gluk", true));
        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        this.repository.findAllOnlyOnline().forEach(System.out::println);

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}