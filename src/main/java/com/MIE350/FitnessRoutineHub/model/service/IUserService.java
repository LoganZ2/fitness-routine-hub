package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.model.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    User getUser(Long id);
    User newUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long id);
}
