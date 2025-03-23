package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.AlreadyFollowedException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.NotFollowedException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }


    @Override
    public List<UsersDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UsersDTO(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDTO(user);
    }

    @Override
    public UserDTO findUserByName(String name) {
        User user = userRepository.findByUsername(name).orElseThrow(UserNotFoundException::new);
        return new UserDTO(user);
    }

    @Override
    public User newUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException();
        }
        user.setCreatedAt(Instant.now());
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User userOld = userRepository.findById(user.getId())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getUsername().equals(userOld.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateUsernameException();
        }

        if (user.getUsername() != null) userOld.setUsername(user.getUsername());
        if (user.getPosts() != null) userOld.setPosts(user.getPosts());
        if (user.getDescription() != null) userOld.setDescription(user.getDescription());
        if (user.getDayInfos() != null) userOld.setDayInfos(user.getDayInfos());
        if (user.getFollowers() != null) userOld.setFollowers(user.getFollowers());
        if (user.getFollowings() != null) userOld.setFollowings(user.getFollowings());
        if (user.getHealthProfile() != null) userOld.setHealthProfile(user.getHealthProfile());

        userOld.setUpdateAt(Instant.now());
        return userRepository.save(userOld);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void follow(Long id, Long followingId) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User following = userRepository.findById(followingId).orElseThrow(() -> new UserNotFoundException());
        if (user.getFollowingsLong().contains(followingId)) {
            throw new AlreadyFollowedException();
        }
        user.getFollowings().add(following);
        following.getFollowers().add(user);
        updateUser(following);
        updateUser(user);
    }

    @Override
    public void unfollow(Long id, Long followingId) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User following = userRepository.findById(followingId).orElseThrow(() -> new UserNotFoundException());
        if (!user.getFollowingsLong().contains(followingId)) {
            throw new NotFollowedException();
        }
        user.getFollowings().remove(following);
        following.getFollowers().remove(user);
        updateUser(following);
        updateUser(user);
    }

    @Override
    public List<UserDTO> getFollowings(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.findAllById(user.getFollowingsLong()).stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userRepository.findAllById(user.getFollowersLong()).stream().map(u -> new UserDTO(u)).collect(Collectors.toList());
    }

}
