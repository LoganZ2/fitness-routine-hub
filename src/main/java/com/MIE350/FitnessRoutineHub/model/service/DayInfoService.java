package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingDayInfoException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.utils.HealthUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class DayInfoService implements IDayInfoService {

    private final UserRepository userRepository;

    public DayInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<DayInfo> getDayInfos(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new).getDayInfos();
    }

    @Override
    public Boolean checkChallengeCompletion(Long id, Instant date) {
        final int threshold = 100;
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<DayInfo> dayInfoList = user.getDayInfos();
        HealthProfile hp = user.getHealthProfile();
        DayInfo dayInfo = dayInfoList.stream().filter(d -> d.getDate().equals(date)).findFirst().orElseThrow(MissingDayInfoException::new);
        int netCalories = dayInfo.getCaloriesIntake() - dayInfo.getCaloriesBurn();
        int netCaloriesRequirement = HealthUtils.calculateNetCalories(hp.getHeight(), hp.getWeight(), hp.getAge(), hp.getObjective());
        if (Math.abs(netCalories - netCaloriesRequirement) >= threshold) {
            dayInfo.setChallengeCompleted(false);
        } else {
            dayInfo.setChallengeCompleted(true);
        }
        updateDayInfo(id, dayInfo);
        return dayInfo.getChallengeCompleted();
    }

    @Override
    public DayInfo updateDayInfo(Long id, DayInfo dayInfo) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<DayInfo> dayInfoList = user.getDayInfos();
        Optional<DayInfo> dayInfoOld = dayInfoList.stream().filter(d -> d.getDate().equals(dayInfo.getDate())).findFirst();
        if (dayInfoOld.isEmpty()) {
            dayInfoList.add(dayInfo);
            userRepository.save(user);
            return dayInfo;
        } else {
            DayInfo di = dayInfoOld.get();
            if (dayInfo.getDate() != null) di.setDate(dayInfo.getDate());
            if (dayInfo.getChallengeCompleted() != null) di.setChallengeCompleted(dayInfo.getChallengeCompleted());
            if (dayInfo.getCaloriesIntake() != null) di.setCaloriesIntake(dayInfo.getCaloriesIntake());
            if (dayInfo.getCaloriesBurn() != null) di.setCaloriesBurn(dayInfo.getCaloriesBurn());
            userRepository.save(user);
            return di;
        }
    }
}
