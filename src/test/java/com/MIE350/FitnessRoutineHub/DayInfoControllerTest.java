package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.DayInfoController;
import com.MIE350.FitnessRoutineHub.controller.dto.DayCaloriesDTO;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.service.IDayInfoService;
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

@WebMvcTest(DayInfoController.class)
public class DayInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDayInfoService dayInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetDayInfos() throws Exception {
        DayInfo info = new DayInfo();
        info.setId(1L);
        info.setDate(Instant.now());
        Mockito.when(dayInfoService.getDayInfos(1L)).thenReturn(Collections.singletonList(info));

        mockMvc.perform(get("/users/day-info/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testCheckChallengeCompletion() throws Exception {
        DayCaloriesDTO dto = new DayCaloriesDTO(
                Instant.now(),
                Map.of("apple", 100.0),
                Map.of("run", 150.0)
        );

        Mockito.when(dayInfoService.checkChallengeCompletion(Mockito.eq(1L), Mockito.any(DayCaloriesDTO.class)))
                .thenReturn(true);

        mockMvc.perform(post("/users/day-info/challenge/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testUpdateDayInfo() throws Exception {
        DayInfo input = new DayInfo();
        input.setId(1L);
        input.setDate(Instant.now());

        Mockito.when(dayInfoService.updateDayInfo(Mockito.eq(1L), Mockito.any(DayInfo.class)))
                .thenReturn(input);

        mockMvc.perform(put("/users/day-info/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
