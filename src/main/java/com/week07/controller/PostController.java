package com.week07.controller;

import com.google.gson.Gson;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.PostReqDto;
import com.week07.dto.request.PostUpdateReqDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    public final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public GlobalResDto<?> createPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestParam(required = false) String data,
                                      @RequestPart(required = false) MultipartFile file) throws IOException {
//        System.out.println(data);
//        System.out.println(file.get(0).getOriginalFilename());
//        System.out.println(file);
        Gson gson = new Gson();
        PostReqDto postReqDto = gson.fromJson(data, PostReqDto.class);
//        System.out.println(postReqDto.getPostContent());
//        return GlobalResDto.success(null,"hello");
        return postService.createPost(postReqDto, userDetails, file);
    }

    @PutMapping("/{postId}")
    public GlobalResDto<?> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId,
                                      @RequestBody PostUpdateReqDto postUpdateReqDto) {
        return postService.updatePost(userDetails, postId, postUpdateReqDto);
    }

    @DeleteMapping("{postId}")
    public GlobalResDto<?> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @PathVariable Long postId) {
        return postService.deletePost(postId, userDetails);
    }
}
