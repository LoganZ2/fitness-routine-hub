package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.DayCaloriesDTO;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;

import java.time.Instant;
import java.util.List;

public interface IDayInfoService {
    List<DayInfo> getDayInfos(Long id);
    Boolean checkChallengeCompletion(Long id, DayCaloriesDTO dayCaloriesDTO);
    DayInfo updateDayInfo(Long id, DayInfo dayInfo);
}
