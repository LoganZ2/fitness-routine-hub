package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void testSaveAndFindById() {
        Post post = new Post();
        post.setTitle("My First Post");
        post.setType(PostType.DISCUSSION);
        post.setBody("Content body");
        post.setCreatedAt(java.time.Instant.now());

        postRepository.save(post);

        Optional<Post> found = postRepository.findById(post.getId());
        assertTrue(found.isPresent());
        assertEquals("My First Post", found.get().getTitle());
    }

    @Test
    void testFindAllByType() {
        Post p1 = new Post();
        p1.setTitle("Tips");
        p1.setType(PostType.GUIDE);
        p1.setBody("Tip body");
        p1.setCreatedAt(java.time.Instant.now());

        Post p2 = new Post();
        p2.setTitle("Ask");
        p2.setType(PostType.QUESTION);
        p2.setBody("Question body");
        p2.setCreatedAt(java.time.Instant.now());

        postRepository.save(p1);
        postRepository.save(p2);

        List<Post> guides = postRepository.findAllByType(PostType.GUIDE);
        assertEquals(1, guides.size());
        assertEquals("Tips", guides.get(0).getTitle());
    }
}
