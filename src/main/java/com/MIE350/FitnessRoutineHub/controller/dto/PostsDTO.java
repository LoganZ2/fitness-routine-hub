package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class PostsDTO {
    private Long id;
    private User user;
    private String title;
    private Post.PostType type;
    private String body;
    private Instant createdAt;
    public PostsDTO(Post post) {
        this.id = post.getId();
        this.user = post.getUser();
        this.title = post.getTitle();
        this.type = post.getType();
        this.body = post.getBody();
        this.createdAt = post.getCreatedAt();
    }
}
