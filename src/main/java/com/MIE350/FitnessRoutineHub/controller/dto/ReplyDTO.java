package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplyDTO {
    private Long postId;
    private String content;
}
