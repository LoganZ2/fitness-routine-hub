package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.exceptions.DuplicateUsernameException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.PostNotFoundException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Post> getPosts() {
        return repository.findAll();
    }

    @Override
    public List<Post> getPostsByType(PostType type) {
        return repository.findAllByType(type);
    }

    @Override
    public Post getPost(Long id) {
        return repository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public Post newPost(Post post) {
        post.setCreatedAt(Instant.now());
        post.setId(null);
        return repository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post postOld = repository.findById(post.getId())
                .orElseThrow(PostNotFoundException::new);
        if (post.getTitle() != null) postOld.setTitle(post.getTitle());
        if (post.getType() != null) postOld.setType(post.getType());
        if (post.getBody() != null) postOld.setBody(post.getBody());
        if (post.getReplies() != null) postOld.setReplies(post.getReplies());
        postOld.setUpdateAt(Instant.now());
        return repository.save(postOld);
    }

    @Override
    public void deletePost(Long id) {
        repository.deleteById(id);
    }
}
