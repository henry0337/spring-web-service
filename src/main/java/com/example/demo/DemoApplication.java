package com.example.demo;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.demo.utilities.properties")
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
//        User adminAccount = userRepository.findByRole(Role.ADMIN);
//
//        if (adminAccount == null) {
//            User user = new User();
//            user.setName("Administrator");
//            user.setEmail("admin@email.com");
//            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
//            user.setRole(Role.ADMIN);
//
//            userRepository.save(user);
//        }
    }
}
