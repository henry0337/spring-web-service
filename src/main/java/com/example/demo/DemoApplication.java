package com.example.demo;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        User adminAccount = userRepository.findByRole(Role.ADMIN);

        if (adminAccount == null) {
            User user = new User();
            user.setName("Administrator");
            user.setEmail("admin@email.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setRole(Role.ADMIN);

            userRepository.save(user);
        }
    }
}
