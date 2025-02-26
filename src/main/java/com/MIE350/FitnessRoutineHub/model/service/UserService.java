package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.AlreadyFriendsException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UsersDTO> getUsers() {
        return repository.findAll()
                .stream()
                .map(user -> new UsersDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElse(null);
        if (user != null) {
            return new UserDTO(user);
        }
        return null;
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
            if (!user.getUsername().equals(userOld.getUsername()) && repository.existsByUsername(user.getUsername())) {
                throw new DuplicateUsernameException();
            }
            userOld.setUsername(user.getUsername());
            userOld.setPosts(user.getPosts());
            userOld.setDescription(user.getDescription());
            userOld.setFitnessCalendar(user.getFitnessCalendar());
            userOld.setFriends(user.getFriends());
            userOld.setUpdateAt(Instant.now());
            return repository.save(userOld);
        }).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addUser(Long id, Long friendId) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        User friend = repository.findById(friendId).orElseThrow(() -> new UserNotFoundException("Friend not found"));
        if (user.getFriendsLong().contains(friendId)) {
            throw new AlreadyFriendsException();
        }
        user.addFriend(friend);
        updateUser(user);
    }

    @Override
    public void removeUser(Long id, Long friendId) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        User friend = repository.findById(friendId).orElseThrow(() -> new UserNotFoundException("Friend not found"));
        user.removeFriend(friend);
        updateUser(user);
    }

    @Override
    public List<UserDTO> getFriends(Long id) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        return repository.findAllById(user.getFriendsLong()).stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    }
}
