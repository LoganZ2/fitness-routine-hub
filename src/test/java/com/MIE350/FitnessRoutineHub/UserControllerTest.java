package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.UserController;
import com.MIE350.FitnessRoutineHub.controller.dto.UserDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsersDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.UsernameDTO;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.service.IDayInfoService;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private IDayInfoService dayInfoService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUsers() throws Exception {
        UsersDTO mockUser = new UsersDTO(1L, "testuser", "desc");
        Mockito.when(userService.getUsers()).thenReturn(Collections.singletonList(mockUser));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    void testAddUser() throws Exception {
        ObjectNode inputUser = objectMapper.createObjectNode();
        inputUser.put("id", 1L);
        inputUser.put("username", "testuser");
        inputUser.put("description", "desc");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        UserDTO userDTO = new UserDTO(2L, "john", "desc", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new ArrayList<>(), Instant.now(), Instant.now(), null);
        Mockito.when(userService.getUser(2L)).thenReturn(userDTO);

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"));
    }

    @Test
    void testLogin() throws Exception {
        UsernameDTO input = new UsernameDTO("john");
        UserDTO result = new UserDTO(3L, "john", "desc", new ArrayList<>(), new HashSet<>(), new HashSet<>(), new ArrayList<>(), Instant.now(), Instant.now(), null);
        Mockito.when(userService.findUserByName("john")).thenReturn(result);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"));
    }
}
