package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingDayInfoException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.utils.HealthUtils;

import java.time.Instant;
import java.util.List;

public class FitnessCalendarService implements IFitnessCalendarService {

    private final UserRepository userRepository;

    public FitnessCalendarService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<DayInfo> getDayInfos(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new).getFitnessCalendar().getDayInfos();
    }

    @Override
    public Boolean checkChallengeCompletion(Long id, Instant date) {
        final int threshold = 100;
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<DayInfo> dayInfoList = user.getFitnessCalendar().getDayInfos();
        HealthProfile hp = user.getHealthProfile();
        DayInfo dayInfo = dayInfoList.stream().filter(d -> d.getDate().equals(date)).findFirst().orElseThrow(MissingDayInfoException::new);
        int netCalories = dayInfo.getCaloriesIntake() - dayInfo.getCaloriesBurn();
        int netCaloriesRequirement = HealthUtils.calculateNetCalories(hp.getHeight(), hp.getWeight(), hp.getAge(), hp.getObjective());
        if (Math.abs(netCalories - netCaloriesRequirement) >= threshold) return false;
        return true;
    }
}
