package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.DayCaloriesDTO;
import com.MIE350.FitnessRoutineHub.controller.exceptions.MissingDayInfoException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import com.MIE350.FitnessRoutineHub.utils.HealthUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
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
    public Boolean checkChallengeCompletion(Long id, DayCaloriesDTO dayCaloriesDTO) {
        final int threshold = 100;
        ZoneId zid = ZoneId.systemDefault();
        Instant date = dayCaloriesDTO.getDate();
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        List<DayInfo> dayInfoList = user.getDayInfos();
        HealthProfile hp = user.getHealthProfile();
        DayInfo newToday = new DayInfo();
        newToday.setDate(date);
        DayInfo dayInfo = dayInfoList.stream().filter(d -> d.getDate().atZone(zid).toLocalDate().equals(date.atZone(zid).toLocalDate())).findFirst().orElse(newToday);
        double netCalories = HealthUtils.calculateTotalCalories(hp.getWeight(), dayCaloriesDTO.getFoodIntake(), dayCaloriesDTO.getExerciseData());
        dayInfo.setNetCalories(netCalories);
        double netCaloriesRequirement = HealthUtils.calculateNetCalories(hp.getHeight(), hp.getWeight(), hp.getAge(), hp.getObjective());
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
            if (dayInfo.getNetCalories() != null) di.setNetCalories(dayInfo.getNetCalories());
            userRepository.save(user);
            return di;
        }
    }
}
