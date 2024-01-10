package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.orElseThrow(() -> new EntityNotFoundException("Không có bản ghi nào thuộc về bản ghi có tên người dùng là " + name + " này!"));
    }

    public User insert(User user, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            user.setImage(imagePath);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        final String uploadDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\img";
        Path path = Paths.get(uploadDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = Objects.requireNonNull(imageFile.getOriginalFilename());
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, imageFile.getBytes());

        return "/img/" + fileName;
    }

    public User update(User user, String name, MultipartFile imageFile) throws IOException {
        Optional<User> currentUser = userRepository.findByName(name);

        if (currentUser.isPresent()) {
            User newUser = currentUser.get();

            newUser.setName(user.getName());
            newUser.setEmail(user.getUsername());
            newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            newUser.setStatus(user.getStatus());
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = saveImage(imageFile);
                newUser.setImage(imagePath);
            }

            return userRepository.save(newUser);
        } else {
            throw new EntityNotFoundException("Can't find any user with this username!");
        }
    }

    public void delete(String username) {
        userRepository.deleteByName(username);
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Can't find any user with this username!")
        );
    }
}
