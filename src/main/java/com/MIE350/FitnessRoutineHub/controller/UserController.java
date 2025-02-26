package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.controller.dto.FriendDTO;
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
    List<UsersDTO> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User user) {
        return userService.newUser(user);
    }

    @GetMapping("/users/{id}")
    UserDTO getUser(@PathVariable("id") Long id) {
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
    void addFriend (@RequestBody FriendDTO friendDTO) {
        userService.addUser(friendDTO.getId(), friendDTO.getFriendId());
    }

    @PatchMapping("/users/remove-friend")
    void removeFriend (@RequestBody FriendDTO friendDTO) {
        userService.removeUser(friendDTO.getId(), friendDTO.getFriendId());
    }

    @GetMapping("/users/friends/{id}")
    List<UserDTO> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }
}
