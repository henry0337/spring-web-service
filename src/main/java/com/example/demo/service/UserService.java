package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        return user.orElseThrow(() -> new EntityNotFoundException("Không có bản ghi nào thuộc về bản ghi có tên người dùng là " + name + " này!"));
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public User update(User user, String name) {
        Optional<User> currentUser = userRepository.findByUsername(name);
        if (currentUser.isPresent()) {
            User newUser = currentUser.get();
            newUser.setName(user.getName());
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setStatus(user.getStatus());
            return userRepository.save(newUser);
        } else {
            throw new EntityNotFoundException("Không tìm thấy người dùng có tên là " + name + " để cập nhật!");
        }
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
}
