package com.MIE350.FitnessRoutineHub.model.entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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
    private User user;

    @Column(nullable = false)
    private String title;

    private PostType type;

    private String body;

    @OneToMany
    private List<User> likes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply> replies;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
