package com.example.demo.controllers;

import com.example.demo.models.User;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        if (userService.findAll().isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        if (userService.findByName(username) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.findByName(username));
    }

    @PostMapping(
            consumes = {MULTIPART_FORM_DATA_VALUE, APPLICATION_JSON_VALUE},
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> store(
            @Validated @ModelAttribute User user,
            @RequestParam("imageFile") MultipartFile imageFile,
            BindingResult result
    ) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        return new ResponseEntity<>(userService.insert(user, imageFile), HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{username}",
            consumes = {MULTIPART_FORM_DATA_VALUE, APPLICATION_JSON_VALUE},
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update(
            @PathVariable String username,
            @Validated @ModelAttribute User user,
            @RequestParam("imageFile") MultipartFile imageFile,
            BindingResult result
    ) throws IOException {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(userService.update(user, username, imageFile));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username) {
        if (userService.findByName(username) == null)
            return ResponseEntity.notFound().build();
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }
}