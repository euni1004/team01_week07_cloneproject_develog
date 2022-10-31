package com.week07.controller;

import com.week07.dto.GlobalResDto;
import com.week07.dto.request.IntroReqDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @PostMapping("/myPage/intro")
    public GlobalResDto<?> postIntro(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody IntroReqDto introReqDto) {
        return myPageService.postIntro(userDetails, introReqDto);
    }

    @PostMapping("/myPage/img")
    public GlobalResDto<?> postImg(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        List<MultipartFile> multipartFiles = multipartHttpServletRequest.getFiles("file");
        System.out.println(multipartFiles.get(0).getOriginalFilename());
        MultipartFile file = multipartFiles.get(0);
        return myPageService.postImg(userDetails, file);
    }

    @GetMapping("/getMyPage")
    public GlobalResDto<?> getMyPage(@RequestParam("id") String userId) {
        return myPageService.getMyPage(userId);
    }
}
