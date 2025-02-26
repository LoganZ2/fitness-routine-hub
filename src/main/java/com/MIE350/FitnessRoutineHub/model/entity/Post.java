package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Posts")
public class Post {

    public enum PostType {
        DISCUSSION,
        QUESTION,
        GUIDE,
        LOG,
        REVIEW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private PostType type;

    private String body;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
