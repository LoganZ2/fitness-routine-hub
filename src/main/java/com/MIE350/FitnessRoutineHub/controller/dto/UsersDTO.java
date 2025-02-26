package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.User;
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
    public UsersDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.description = user.getDescription();
    }
}
