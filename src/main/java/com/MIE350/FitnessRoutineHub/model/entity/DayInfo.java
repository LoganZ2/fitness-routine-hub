package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Map;

@Entity
@Getter
@Setter
public class DayInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private Instant date;

    private Boolean challengeCompleted;

    private Double netCalories;

}
