package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.AlreadyFriendsException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.beans.BeanUtils;
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
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDTO(user);
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
        User userOld = repository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getUsername().equals(userOld.getUsername()) && repository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException();
        }

        if (user.getUsername() != null) userOld.setUsername(user.getUsername());
        if (user.getPosts() != null) userOld.setPosts(user.getPosts());
        if (user.getDescription() != null) userOld.setDescription(user.getDescription());
        if (user.getFitnessCalendar() != null) userOld.setFitnessCalendar(user.getFitnessCalendar());
        if (user.getFriends() != null) userOld.setFriends(user.getFriends());

        userOld.setUpdateAt(Instant.now());
        return repository.save(userOld);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        User user = repository.findById(id).orElseThrow(UserNotFoundException::new);
        User friend = repository.findById(friendId).orElseThrow(() -> new UserNotFoundException("Friend not found"));
        if (user.getFriendsLong().contains(friendId)) {
            throw new AlreadyFriendsException();
        }
        user.addFriend(friend);
        updateUser(user);
    }

    @Override
    public void removeFriend(Long id, Long friendId) {
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
