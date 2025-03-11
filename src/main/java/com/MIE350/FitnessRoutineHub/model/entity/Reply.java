package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Data
@Table(name = "Replies")
public class Reply {

    @EmbeddedId
    private ReplyId replyId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @NotNull
    private String content;

    private Integer likes;

    @NotNull
    private Instant createdAt;
}
