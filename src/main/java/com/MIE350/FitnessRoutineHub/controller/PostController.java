package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.controller.dto.LikeDTO;
import com.MIE350.FitnessRoutineHub.controller.dto.ReplyDTO;
import com.MIE350.FitnessRoutineHub.model.entity.Post;
import com.MIE350.FitnessRoutineHub.model.entity.Post.PostType;
import com.MIE350.FitnessRoutineHub.model.entity.Reply;
import com.MIE350.FitnessRoutineHub.model.service.IPostService;
import com.MIE350.FitnessRoutineHub.model.service.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/posts")
public class PostController {

    private final IPostService postService;
    private final IUserService userService;

    public PostController(IPostService postService, IUserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public List<Post> getPosts(@RequestParam(required = false) PostType type) {
        return (type == null) ? postService.getPosts() : postService.getPostsByType(type);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public Post newPost(@RequestBody Post post) {
        return postService.newPost(post);
    }

    @PatchMapping
    public Post updatePost(@RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @PostMapping("/like")
    public boolean addLike(@RequestBody LikeDTO like) {
        return postService.addLike(like.getPostId(), like.getUserId());
    }

    @PostMapping("/reply")
    public String addReply(@RequestBody ReplyDTO replyDTO) {
        postService.addReply(replyDTO.getPostId(), replyDTO.getContent());
        return "done";
    }
}
