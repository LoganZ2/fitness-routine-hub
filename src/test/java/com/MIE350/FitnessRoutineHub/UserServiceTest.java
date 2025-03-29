package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.model.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUsers() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setDescription("desc");

        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UsersDTO> result = userService.getUsers();
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
    }

    @Test
    void testGetUserById_found() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setCreatedAt(Instant.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = userService.getUser(1L);
        assertEquals("testuser", dto.getUsername());
    }

    @Test
    void testGetUserById_notFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(2L));
    }

    @Test
    void testNewUser_duplicateUsername() {
        User user = new User();
        user.setUsername("duplicate");

        when(userRepository.existsByUsername("duplicate")).thenReturn(true);

        assertThrows(DuplicateUsernameException.class, () -> userService.newUser(user));
    }

    @Test
    void testNewUser_success() {
        User user = new User();
        user.setUsername("unique");

        when(userRepository.existsByUsername("unique")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        UserDTO result = userService.newUser(user);
        assertEquals("unique", result.getUsername());
    }
}
