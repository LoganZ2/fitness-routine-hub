package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class FitnessCalendar {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "fitnessCalendar")
    @JsonManagedReference
    private List<DayInfo> dayInfos;

}
