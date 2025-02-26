package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ReplyId implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "level")
    private Integer level;

    public ReplyId(Long postId, Integer level) {
        this.postId = postId;
        this.level = level;
    }

    public ReplyId() {}
}
