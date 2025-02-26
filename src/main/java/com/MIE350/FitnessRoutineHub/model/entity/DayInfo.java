package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class DayInfo {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fitness_calendar_id")
    @JsonBackReference
    private FitnessCalendar fitnessCalendar;

    private Instant date;

    private Boolean challengeCompleted;

    private Integer caloriesIntake;

    private Integer caloriesBurn;

}
