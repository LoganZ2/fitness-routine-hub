package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setDescription("Alice's profile");
        user.setCreatedAt(java.time.Instant.now());

        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("alice");
        assertTrue(found.isPresent());
        assertEquals("Alice's profile", found.get().getDescription());
    }

    @Test
    void testExistsByUsername() {
        User user = new User();
        user.setUsername("bob");
        user.setDescription("Bob's profile");
        user.setCreatedAt(java.time.Instant.now());
        userRepository.save(user);

        boolean exists = userRepository.existsByUsername("bob");
        assertTrue(exists);

        boolean notExists = userRepository.existsByUsername("charlie");
        assertFalse(notExists);
    }
}
