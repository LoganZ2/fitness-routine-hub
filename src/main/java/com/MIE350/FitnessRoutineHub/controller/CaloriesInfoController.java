package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.utils.HealthUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/calories-info")
public class CaloriesInfoController {
    @GetMapping("/food-cal")
    public Map<String, Integer> getFoodCalories() {
        return HealthUtils.foodCalories();
    }

    @GetMapping("/exercise-cal")
    public Map<String, Integer> getExerciseBurn() {
        return HealthUtils.exerciseBurn();
    }
}
