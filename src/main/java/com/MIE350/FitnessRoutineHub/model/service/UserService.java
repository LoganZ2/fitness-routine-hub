package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User newUser(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException();
        }
        user.setCreatedAt(Instant.now());
        user.setId(null);
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        Long userId = user.getId();
        return repository.findById(userId).map(userOld -> {
            boolean needsUpdate = false;

            if (!user.equals(userOld)) {
                if (!user.getUsername().equals(userOld.getUsername()) && repository.existsByUsername(user.getUsername())) {
                    throw new DuplicateUsernameException();
                }
                userOld.setUsername(user.getUsername());
                userOld.setPosts(user.getPosts());
                userOld.setDescription(user.getDescription());
                needsUpdate = true;
            }

            if (needsUpdate) {
                userOld.setUpdateAt(Instant.now());
                return repository.save(userOld);
            } else {
                return userOld;
            }
        }).orElseThrow(() -> new UserNotFoundException());
    }


    @Override
    public boolean deleteUser(Long id) {
        repository.deleteById(id);
        return true;
    }
}
