package com.MIE350.FitnessRoutineHub.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @NotNull
    private Instant createdAt;

    private Instant updateAt;

}
