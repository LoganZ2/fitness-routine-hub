package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;
import org.apache.tomcat.util.json.JSONParser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    private String body;

    @OneToMany(mappedBy = "post_id")
    private List<Reply> replies;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
