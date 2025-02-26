package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.FitnessCalendar;
import com.MIE350.FitnessRoutineHub.model.entity.Post;

import com.MIE350.FitnessRoutineHub.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
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
    private FitnessCalendar fitnessCalendar;
    private Instant createdAt;
    private Instant updateAt;
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.posts = user.getPosts();
        this.friends = user.getFriendsLong();
        this.fitnessCalendar = user.getFitnessCalendar();
        this.createdAt = user.getCreatedAt();
        this.updateAt = user.getUpdateAt();
    }
}
