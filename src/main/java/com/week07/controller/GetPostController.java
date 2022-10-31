package com.week07.controller;

import com.week07.dto.GlobalResDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.GetPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetPostController {

    private final GetPostService getPostService;

    @GetMapping("/getAllPost")
    public GlobalResDto<?> getAllPost() {
        return getPostService.getAllPost();
    }

    @GetMapping("/getPost/{postId}")
    public GlobalResDto<?> getOnePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return getPostService.getOnePost(userDetails, postId);
    }
}
