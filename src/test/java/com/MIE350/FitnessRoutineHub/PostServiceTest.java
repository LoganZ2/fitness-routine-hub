package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.dto.PostDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.PostsDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.PostNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.model.service.PostService;
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
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testGetPosts() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Sample");

        when(postRepository.findAll()).thenReturn(List.of(post));

        List<PostsDTO> result = postService.getPosts();
        assertEquals(1, result.size());
        assertEquals("Sample", result.get(0).getTitle());
    }

    @Test
    void testGetPostsByType() {
        Post post = new Post();
        post.setType(PostType.DISCUSSION);

        when(postRepository.findAllByType(PostType.DISCUSSION)).thenReturn(List.of(post));

        List<PostsDTO> result = postService.getPostsByType(PostType.DISCUSSION);
        assertEquals(1, result.size());
        assertEquals(PostType.DISCUSSION, result.get(0).getType());
    }

    @Test
    void testGetPost_found() {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Hello");

        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        PostDTO result = postService.getPost(1L);
        assertEquals("Hello", result.getTitle());
    }

    @Test
    void testGetPost_notFound() {
        when(postRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(PostNotFoundException.class, () -> postService.getPost(999L));
    }

    @Test
    void testNewPost_success() {
        Post post = new Post();
        post.setTitle("New Post");

        when(postRepository.save(any(Post.class))).thenAnswer(inv -> inv.getArgument(0));

        Post saved = postService.newPost(post);
        assertEquals("New Post", saved.getTitle());
        assertNotNull(saved.getCreatedAt());
    }
}
