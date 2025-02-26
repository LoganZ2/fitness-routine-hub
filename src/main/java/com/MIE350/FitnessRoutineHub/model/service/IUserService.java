package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.model.entity.User;

import java.util.List;

public interface IUserService {
    List<UsersDTO> getUsers();
    UserDTO getUser(Long id);
    User newUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    void addUser(Long id, Long friendId);
    void removeUser(Long id, Long friendId);
    List<UserDTO> getFriends(Long id);
}
