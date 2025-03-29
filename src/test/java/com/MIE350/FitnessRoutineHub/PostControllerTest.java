package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.PostController;
import com.MIE350.FitnessRoutineHub.controller.dto.PostDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.PostsDTO;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.service.IPostService;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.*;

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
        User mockUser = new User();
        PostsDTO post1 = new PostsDTO(1L, mockUser, "title", PostType.DISCUSSION, "desc", Instant.now());
        Mockito.when(postService.getPosts()).thenReturn(Collections.singletonList(post1));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("title"));
    }

    @Test
    void testGetPostById() throws Exception {
        User mockUser = new User();
        PostDTO post = new PostDTO(1L, mockUser, "title", PostType.DISCUSSION, "desc",
                new ArrayList<>(), new ArrayList<>(), Instant.now(), Instant.now());
        Mockito.when(postService.getPost(1L)).thenReturn(post);

        mockMvc.perform(get("/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"));
    }

    @Test
    void testAddPost() throws Exception {
        Post inputPost = new Post();
        inputPost.setId(1L);
        inputPost.setTitle("title");
        inputPost.setBody("desc");
        inputPost.setType(PostType.DISCUSSION);

        Mockito.when(postService.newPost(Mockito.any(Post.class))).thenReturn(inputPost);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPost)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"));
    }
}
