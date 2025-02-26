package com.MIE350.FitnessRoutineHub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String username;
    private String description;
}
