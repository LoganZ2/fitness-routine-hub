package com.MIE350.FitnessRoutineHub;

import com.MIE350.FitnessRoutineHub.controller.dto.DayCaloriesDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.model.service.DayInfoService;
import com.MIE350.FitnessRoutineHub.utils.HealthUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DayInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DayInfoService dayInfoService;

    @Test
    void testGetDayInfos_success() {
        DayInfo info = new DayInfo();
        info.setId(1L);
        User user = new User();
        user.setDayInfos(List.of(info));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<DayInfo> result = dayInfoService.getDayInfos(1L);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void testGetDayInfos_userNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> dayInfoService.getDayInfos(2L));
    }

    @Test
    void testCheckChallengeCompletion_success() {
        DayCaloriesDTO dto = new DayCaloriesDTO(
                Instant.now(),
                Map.of("apple", 100.0),
                Map.of("run", 50.0)
        );

        User user = new User();
        HealthProfile hp = new HealthProfile();
        hp.setWeight(70);
        user.setHealthProfile(hp);
        user.setDayInfos(new ArrayList<>());

        try (MockedStatic<HealthUtils> mocked = mockStatic(HealthUtils.class)) {
            mocked.when(() -> HealthUtils.calculateTotalCalories(eq(70), anyMap(), anyMap())).thenReturn(85.0);
            when(userRepository.findById(1L)).thenReturn(Optional.of(user));

            Boolean result = dayInfoService.checkChallengeCompletion(1L, dto);
            assertTrue(result);
        }
    }
}
