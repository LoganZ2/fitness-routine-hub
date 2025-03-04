package com.MIE350.FitnessRoutineHub.model.service;

import com.MIE350.FitnessRoutineHub.controller.exceptions.PostNotFoundException;
import com.MIE350.FitnessRoutineHub.controller.exceptions.UserNotFoundException;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.entity.User;
import com.MIE350.FitnessRoutineHub.model.repository.PostRepository;
import com.MIE350.FitnessRoutineHub.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByType(PostType type) {
        return postRepository.findAllByType(type);
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public Post newPost(Post post) {
        post.setCreatedAt(Instant.now());
        post.setId(null);
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        Post postOld = getPost(post.getId());
        if (post.getTitle() != null) postOld.setTitle(post.getTitle());
        if (post.getType() != null) postOld.setType(post.getType());
        if (post.getBody() != null) postOld.setBody(post.getBody());
        if (post.getReplies() != null) postOld.setReplies(post.getReplies());
        if (post.getLikes() != null) postOld.setLikes(post.getLikes());
        postOld.setUpdateAt(Instant.now());
        return postRepository.save(postOld);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public boolean addLike(Long id, Long userId) {
        Post post = getPost(id);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        if (post.getLikes().contains(user)) return false;
        post.getLikes().add(user);
        updatePost(post);
        return true;
    }

    @Override
    public void addReply(Long id, Reply reply) {
        Post post = getPost(id);
        post.getReplies().add(reply);
        updatePost(post);
    }
}
