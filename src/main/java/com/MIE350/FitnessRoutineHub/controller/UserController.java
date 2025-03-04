package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.controller.dto.FriendDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.service.IDayInfoService;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final IDayInfoService fitnessCalendarService;

    public UserController(IUserService userService, IDayInfoService fitnessCalendarService) {
        this.userService = userService;
        this.fitnessCalendarService = fitnessCalendarService;
    }

    @GetMapping
    List<UsersDTO> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    User newUser(@RequestBody User user) {
        return userService.newUser(user);
    }

    @GetMapping("/{id}")
    UserDTO getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PatchMapping
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/add-friend")
    void addFriend (@RequestBody FriendDTO friendDTO) {
        userService.addFriend(friendDTO.getId(), friendDTO.getFriendId());
    }

    @PatchMapping("/remove-friend")
    void removeFriend (@RequestBody FriendDTO friendDTO) {
        userService.removeFriend(friendDTO.getId(), friendDTO.getFriendId());
    }

    @GetMapping("/friends/{id}")
    List<UserDTO> getFriends(@PathVariable Long id) {
        return userService.getFriends(id);
    }

}
