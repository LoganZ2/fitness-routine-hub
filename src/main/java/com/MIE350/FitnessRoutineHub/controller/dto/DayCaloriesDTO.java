package com.MIE350.FitnessRoutineHub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class DayCaloriesDTO {
    @NotNull
    public Instant date;
    @NotNull
    public Map<String, Double> foodIntake;
    @NotNull
    public Map<String, Double> exerciseData;
}
