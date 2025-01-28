package com.MIE350.FitnessRoutineHub.controller;


import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        newUser.setCreatedAt(Instant.now());
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}")
    User retrieveUser(@PathVariable("id") Long userId) {
        return repository.findById(userId).orElse(null);
    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable("id") Long userId) {

        return repository.findById(userId)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setUpdateAt(Instant.now());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(userId);
                    newUser.setCreatedAt(Instant.now());
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long userId) {
        repository.deleteById(userId);
    }
}
