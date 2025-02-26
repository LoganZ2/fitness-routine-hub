package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UsersDTO> retrieveAllUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user) {
        return userService.newUser(user);
    }

    @GetMapping("/users/{id}")
    UserDTO retrieveUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PatchMapping("/users")
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
    @PatchMapping("/users/add-friend")
    void addFriend (@RequestParam Long id, @RequestParam Long friendId) {
        userService.addUser(id, friendId);
    }

    @PatchMapping("/users/remove-friend")
    void removeFriend (@RequestParam Long id, @RequestParam Long friendId) {
        userService.removeUser(id, friendId);
    }
}
