package com.MIE350.FitnessRoutineHub.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeDTO {
    Long postId;
    Long userId;
}
