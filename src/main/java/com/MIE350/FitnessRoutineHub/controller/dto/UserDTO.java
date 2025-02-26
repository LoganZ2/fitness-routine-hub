package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String description;
    private List<Post> posts;
    private Set<Long> friends;
    private Instant createdAt;
    private Instant updateAt;
}
