package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.entity.User;

import java.util.List;

public interface IPostService {
    List<Post> getPosts();
    List<Post> getPostsByType(PostType type);
    Post getPost(Long id);
    Post newPost(Post post);
    Post updatePost(Post post);
    void deletePost(Long id);
    boolean addLike(Long id, Long userId);
    void addReply(Long id, Reply reply);
}
