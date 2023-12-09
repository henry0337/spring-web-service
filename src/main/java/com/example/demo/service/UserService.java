package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException("Không có bản ghi nào thuộc về id " + id + " này!"));
    }

    public User insert(User user) {
        return userRepository.save(user);
    }

    public User update(User user, Long id) {
        Optional<User> currentUser = userRepository.findById(id);
        if (currentUser.isPresent()) {
            User newUser = currentUser.get();
            newUser.setName(user.getName());
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setStatus(user.getStatus());
            return userRepository.save(newUser);
        } else {
            throw new EntityNotFoundException("Không tìm thấy người dùng thuộc về id " + id + " để cập nhật!");
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
