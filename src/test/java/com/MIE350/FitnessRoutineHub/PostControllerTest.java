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
import org.mockito.Mockito;
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
        // 构造返回对象
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");

        PostsDTO post = new PostsDTO(1L, user, "test title", PostType.DISCUSSION, "test body", Instant.now());

        when(postService.getPosts()).thenReturn(List.of(post));

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
                new ArrayList<>(),    // likes
                new ArrayList<>(),    // replies
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
        // 输入的 JSON map
        Map<String, Object> input = new HashMap<>();
        input.put("title", "test post");
        input.put("body", "post body");
        input.put("type", "DISCUSSION");
        input.put("user", Map.of("id", 1L));

        // userService 返回 UserDTO（Controller 会用 post.getUser().getId()）
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");

        // postService.newPost 返回 Post 实体（controller 内部会转 DTO）
        Post savedPost = new Post();
        savedPost.setId(99L);
        savedPost.setTitle("test post");
        savedPost.setBody("post body");
        savedPost.setType(PostType.DISCUSSION);
        savedPost.setUser(user);

        // Mock service 方法
        doReturn(user).when(userService).getUser(1L);
        doReturn(savedPost).when(postService).newPost(any(Post.class));


        // 请求 + 验证
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test post"));
    }




}
