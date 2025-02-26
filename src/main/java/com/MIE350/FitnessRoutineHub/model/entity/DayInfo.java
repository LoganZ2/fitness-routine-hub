package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class DayInfo {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fitness_calendar_id")
    private FitnessCalendar fitnessCalendar;

    private Instant date;

    private Boolean challengeCompleted;

    private Integer caloriesIntake;

    private Integer caloriesBurn;

}
