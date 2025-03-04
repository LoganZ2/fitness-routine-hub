package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;

import java.time.Instant;
import java.util.List;

public interface IFitnessCalendarService {
    List<DayInfo> getDayInfos(Long id);
    Boolean checkChallengeCompletion(Long id, Instant date);
}
