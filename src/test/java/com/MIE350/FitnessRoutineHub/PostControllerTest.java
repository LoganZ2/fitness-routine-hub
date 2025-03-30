package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.PostController;
import com.MIE350.FitnessRoutineHub.controller.dto.PostDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.PostsDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.service.IPostService;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPostService postService;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPosts() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");

        PostsDTO post = new PostsDTO(1L, user, "test title", PostType.DISCUSSION, "test body", Instant.now());

        List<PostsDTO> posts = new ArrayList<>();
        posts.add(post);

        when(postService.getPosts()).thenReturn(posts);

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("test title"));
    }

    @Test
    void testGetPostById() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");

        PostDTO post = new PostDTO(
                1L,
                user,
                "sample title",
                PostType.DISCUSSION,
                "sample body",
                new ArrayList<>(),
                new ArrayList<>(),
                Instant.now(),
                Instant.now()
        );

        when(postService.getPost(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("sample title"));
    }

    @Test
    void testAddPost() throws Exception {
        Map<String, Object> input = new HashMap<>();
        input.put("title", "test post");
        input.put("body", "post body");
        input.put("type", "DISCUSSION");
        input.put("user", Map.of("id", 1L));

        UserDTO mockUserDTO = new UserDTO(
                1L,
                "tester",
                "desc",
                new ArrayList<>(),
                new HashSet<>(),
                new HashSet<>(),
                new ArrayList<>(),
                Instant.now(),
                Instant.now(),
                null
        );

        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setUsername("tester");

        Post savedPost = new Post();
        savedPost.setId(99L);
        savedPost.setTitle("test post");
        savedPost.setBody("post body");
        savedPost.setType(PostType.DISCUSSION);
        savedPost.setUser(userEntity);

        doReturn(mockUserDTO).when(userService).getUser(1L);
        doReturn(savedPost).when(postService).newPost(any(Post.class));

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(content().string("99"));
    }


}
