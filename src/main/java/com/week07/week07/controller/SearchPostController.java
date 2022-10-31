package com.week07.week07.controller;

import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.security.UserDetailsImpl;
import com.week07.week07.service.GetPostService;
import com.week07.week07.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchPostController {

    private final GetPostService getPostService;

    @GetMapping
    public GlobalResDto<?> searchPost(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestParam(name = "content") String content) {
        return getPostService.searchPost(userDetails,content);
    }
}
