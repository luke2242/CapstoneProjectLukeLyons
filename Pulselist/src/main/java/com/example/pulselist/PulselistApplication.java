package com.example.pulselist;

import com.example.pulselist.domains.dto.UserDTO;
import com.example.pulselist.domains.entities.User;
import com.example.pulselist.domains.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PulselistApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(PulselistApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Test
        User newUserTest = new User("fakeID", "JohnnySmith2000");

        userRepository.save(newUserTest);


    }
}
