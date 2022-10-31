package com.week07.week07.controller;

import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.security.UserDetailsImpl;
import com.week07.week07.service.GetPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/getPost")
public class GetPostController {

    private final GetPostService getPostService;

    @GetMapping
    public GlobalResDto<?> getAllPost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return getPostService.getAllPost(userDetails);
    }

    @GetMapping("/{postId}")
    public GlobalResDto<?> getOnePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId) {
        return getPostService.getOnePost(userDetails, postId);
    }
}
