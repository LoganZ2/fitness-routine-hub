package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void testSaveAndFindById() {
        User user = new User();
        user.setUsername("tester");
        user.setDescription("Test user");
        user.setCreatedAt(Instant.now());
        userRepository.save(user);

        Post post = new Post();
        post.setTitle("My First Post");
        post.setType(PostType.DISCUSSION);
        post.setBody("Content body");
        post.setCreatedAt(Instant.now());
        post.setUser(user);

        postRepository.save(post);

        Optional<Post> found = postRepository.findById(post.getId());
        assertTrue(found.isPresent());
        assertEquals("My First Post", found.get().getTitle());
    }


    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllByType() {
        User user = new User();
        user.setUsername("jane_testFindAllByType");
        user.setDescription("Jane's profile");
        user.setCreatedAt(Instant.now());

        userRepository.save(user);

        Post p1 = new Post();
        p1.setTitle("Guide title");
        p1.setBody("Guide body");
        p1.setType(PostType.GUIDE);
        p1.setCreatedAt(Instant.now());
        p1.setUser(user);

        Post p2 = new Post();
        p2.setTitle("Question title");
        p2.setBody("Question body");
        p2.setType(PostType.QUESTION);
        p2.setCreatedAt(Instant.now());
        p2.setUser(user);

        postRepository.save(p1);
        postRepository.save(p2);

        List<Post> guides = postRepository.findAllByType(PostType.GUIDE)
                .stream()
                .filter(p -> p.getUser().getUsername().equals("jane_testFindAllByType"))
                .collect(Collectors.toList());

        assertEquals(1, guides.size());
        assertEquals("Guide title", guides.get(0).getTitle());
    }


}
