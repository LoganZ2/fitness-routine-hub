package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.entity.User;

import java.util.List;

public interface IUserService {
    List<UsersDTO> getUsers();
    UserDTO getUser(Long id);
    UserDTO findUserByName(String name);
    User newUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    void addFriend(Long id, Long friendId);
    void removeFriend(Long id, Long friendId);
    List<UserDTO> getFriends(Long id);
}
