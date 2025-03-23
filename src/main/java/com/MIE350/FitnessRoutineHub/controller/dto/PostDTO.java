package com.MIE350.FitnessRoutineHub.controller.dto;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private User user;
    private String title;
    private Post.PostType type;
    private String body;
    private List<User> likes;
    private List<Reply> replies;
    private Instant createdAt;
    private Instant updateAt;
    public PostDTO(Post post) {
        this.id = post.getId();
        this.user = post.getUser();
        this.title = post.getTitle();
        this.type = post.getType();
        this.body = post.getBody();
        this.likes = post.getLikes();
        this.replies = post.getReplies();
        this.createdAt = post.getCreatedAt();
    }
}
