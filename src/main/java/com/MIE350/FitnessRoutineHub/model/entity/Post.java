package com.MIE350.FitnessRoutineHub.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;

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
    private Instant createdAt;

    private Instant updateAt;

}
