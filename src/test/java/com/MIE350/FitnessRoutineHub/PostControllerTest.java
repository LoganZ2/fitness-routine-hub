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
        // 1. 构造前端传入的 JSON 数据
        Map<String, Object> input = new HashMap<>();
        input.put("title", "test post");
        input.put("body", "post body");
        input.put("type", "DISCUSSION");
        input.put("user", Map.of("id", 1L)); // controller 会 post.getUser().getId()

        // 2. 构造完整 User 实体（必须包含 username）
        User user = new User();
        user.setId(1L);
        user.setUsername("tester");
        user.setDescription("desc");
        user.setPosts(new ArrayList<>());
        user.setFollowers(new HashSet<>());
        user.setFollowings(new HashSet<>());
        user.setDayInfos(new ArrayList<>());
        user.setCreatedAt(Instant.now());
        user.setUpdateAt(Instant.now());
        user.setHealthProfile(null);

        // 3. 构造返回的 Post 实体（controller 最终返回 PostDTO）
        Post savedPost = new Post();
        savedPost.setId(99L);
        savedPost.setTitle("test post");
        savedPost.setBody("post body");
        savedPost.setType(PostType.DISCUSSION);
        savedPost.setCreatedAt(Instant.now());
        savedPost.setUpdateAt(Instant.now());
        savedPost.setUser(user);

        // 4. Mock 行为
        when(userService.getUser(1L)).thenReturn(new UserDTO(user)); // 注意返回的是 UserDTO
        when(postService.newPost(any(Post.class))).thenReturn(savedPost);

        // 5. 执行请求并断言返回值
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test post"))
                .andExpect(jsonPath("$.id").value(99));
    }


}
