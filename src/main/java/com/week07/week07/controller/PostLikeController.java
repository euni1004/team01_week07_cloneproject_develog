package com.week07.week07.controller;

import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.security.UserDetailsImpl;
import com.week07.week07.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public GlobalResDto<?> createPostLike(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long postId) {
        return postLikeService.createPostLike(userDetails, postId);
    }

    @DeleteMapping("/{postId}")
    public GlobalResDto<?> deletePostLike(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                          @PathVariable Long postId) {
        return postLikeService.deletePostLike(userDetails, postId);
    }
}
