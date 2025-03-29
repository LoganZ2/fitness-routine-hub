package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.CaloriesInfoController;
import com.MIE350.FitnessRoutineHub.utils.HealthUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CaloriesInfoController.class)
public class CaloriesInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetFoodCaloriesList() throws Exception {
        Map<String, Double> mockFoodCals = Map.of("apple", 95.0, "banana", 105.0);
        try (MockedStatic<HealthUtils> mocked = mockStatic(HealthUtils.class)) {
            mocked.when(HealthUtils::loadFoodCalories).thenReturn(mockFoodCals);

            mockMvc.perform(get("/calories-info/food-cal")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.apple").value(95.0))
                    .andExpect(jsonPath("$.banana").value(105.0));
        }
    }

    @Test
    void testGetExerciseCaloriesList() throws Exception {
        Map<String, Double> mockExerciseCals = Map.of("run", 10.0, "swim", 8.5);
        try (MockedStatic<HealthUtils> mocked = mockStatic(HealthUtils.class)) {
            mocked.when(HealthUtils::loadExerciseCaloriesPerKg).thenReturn(mockExerciseCals);

            mockMvc.perform(get("/calories-info/exercise-cal")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.run").value(10.0))
                    .andExpect(jsonPath("$.swim").value(8.5));
        }
    }
}
