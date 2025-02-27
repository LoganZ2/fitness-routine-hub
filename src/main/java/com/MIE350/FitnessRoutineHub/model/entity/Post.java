package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false)
    private String title;

    private PostType type;

    private String body;

    private Integer likes;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
