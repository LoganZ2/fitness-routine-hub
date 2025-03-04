package com.MIE350.FitnessRoutineHub.model.entity;

import com.MIE350.FitnessRoutineHub.utils.UnitUtils.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class HealthProfile {

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    public enum Objective {
        BULK,
        CUT,
        MAINTAIN
    }

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    private Gender gender;

    private Integer height;

    private HeightUnit heightUnit;

    private Integer weight;

    private WeightUnit weightUnit;

    private Integer age;

    private Objective objective;

}



