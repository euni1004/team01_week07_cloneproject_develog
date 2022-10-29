package com.week07.controller;

import com.google.gson.Gson;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.PostReqDto;
import com.week07.dto.request.PostUpdateReqDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class postController {

    public final PostService postService;

    @PostMapping
    public GlobalResDto<?> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestParam(required = false) String content
//                                      @RequestPart(required = false) MultipartFile multipartFile
    ) {
        Gson gson = new Gson();
        PostReqDto postReqDto = gson.fromJson(content, PostReqDto.class);
        MultipartFile multipartFile =null;
        return postService.createPost(postReqDto, userDetails, multipartFile);
    }

    @PutMapping("/{postId}")
    public GlobalResDto<?> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId,
                                      @RequestParam(required = false) String content,
                                      @RequestPart(required = false) MultipartFile multipartFile) {
        Gson gson = new Gson();
        PostUpdateReqDto postUpdateReqDto = gson.fromJson(content, PostUpdateReqDto.class);
        return postService.updatePost(postUpdateReqDto, userDetails, multipartFile, postId);
    }

    @DeleteMapping("{postId}")
    public GlobalResDto<?> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId) {
        return postService.deletePost(postId,userDetails);
    }
}
