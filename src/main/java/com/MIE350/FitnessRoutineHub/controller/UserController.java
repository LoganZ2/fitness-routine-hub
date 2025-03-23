package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.controller.dto.FriendDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsernameDTO;
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

    @PostMapping("/login")
    UserDTO login(@RequestBody UsernameDTO username) {
        System.out.println(username.getUsername());
        return userService.findUserByName(username.getUsername());
    }

    @PatchMapping
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PatchMapping("/follow")
    void follow (@RequestBody FriendDTO friendDTO) {
        userService.follow(friendDTO.getId(), friendDTO.getFriendId());
    }

    @PatchMapping("/unfollow")
    void unfollow (@RequestBody FriendDTO friendDTO) {
        userService.unfollow(friendDTO.getId(), friendDTO.getFriendId());
    }

    @GetMapping("/followers/{id}")
    List<UserDTO> getFollowers(@PathVariable Long id) {
        return userService.getFollowers(id);
    }

    @GetMapping("/followings/{id}")
    List<UserDTO> getFollowings(@PathVariable Long id) {
        return userService.getFollowings(id);
    }

}
