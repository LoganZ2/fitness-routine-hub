package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyId implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "level")
    private Integer level;

}
