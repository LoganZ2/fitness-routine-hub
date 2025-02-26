package com.MIE350.FitnessRoutineHub.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class FitnessCalendar {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "fitnessCalendar")
    private List<DayInfo> dayInfos;

}
