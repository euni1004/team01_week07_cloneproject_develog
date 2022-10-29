package com.week07.week07.controller;

import com.week07.week07.dto.request.request.LoginReqDto;
import com.week07.week07.dto.request.request.MemberReqDto;
import com.week07.week07.dto.request.request.idCheckDto;
import com.week07.week07.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    //아이디 체크
    @PostMapping("/member/idCheck")
    public ResponseEntity<?> idCheck(@RequestBody idCheckDto idCheckDto){
        return memberService.idCheck(idCheckDto);
    }

    //회원가입
    @PostMapping("/member/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberReqDto memberReqDto){
        return memberService.signup(memberReqDto);
    }

    //로그인
    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReqDto loginReqDto, HttpServletResponse response){
        return memberService.login(loginReqDto,response);
    }
}
