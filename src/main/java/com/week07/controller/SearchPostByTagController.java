package com.week07.controller;

import com.week07.dto.GlobalResDto;
import com.week07.service.SearchPostByTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/searchTag")
public class SearchPostByTagController {

    private final SearchPostByTagService searchPostByTagService;

    @GetMapping("/user")
    public GlobalResDto<?> searchMyPostByTag(@RequestParam("tag") String tag,
                                             @RequestParam("id") String userId) {
        return searchPostByTagService.searchMyPostByTag(tag,userId);
    }//  /team01/searchTag/user?tag=tag&id=userId

    @GetMapping("/all")
    public GlobalResDto<?> searchAllPostByTag(@RequestParam(name = "tag") String tag) {
        return searchPostByTagService.searchAllPostByTag(tag);
    }
}
