package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.entity.HealthProfile;
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
    private List<DayInfo> dayInfos;
    private Instant createdAt;
    private Instant updateAt;
    private HealthProfile healthProfile;
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.posts = user.getPosts();
        this.friends = user.getFriendsLong();
        this.dayInfos = user.getDayInfos();
        this.createdAt = user.getCreatedAt();
        this.updateAt = user.getUpdateAt();
        this.healthProfile = user.getHealthProfile();
    }
}
