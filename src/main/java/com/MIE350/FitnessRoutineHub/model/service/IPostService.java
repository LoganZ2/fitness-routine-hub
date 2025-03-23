package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.dto.PostsDTO;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;

import java.util.List;

public interface IPostService {
    List<PostsDTO> getPosts();
    List<PostsDTO> getPostsByType(PostType type);
    Post getPost(Long id);
    Post newPost(Post post);
    Post updatePost(Post post);
    void deletePost(Long id);
    boolean addLike(Long id, Long userId);
    void addReply(Long id, String content);
}
