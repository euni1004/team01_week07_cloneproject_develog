package com.week07.week07.service;

import com.week07.week07.domain.Member;
import com.week07.week07.domain.RefreshToken;
import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.dto.request.LoginReqDto;
import com.week07.week07.dto.request.MemberReqDto;
import com.week07.week07.dto.request.idCheckDto;
import com.week07.week07.dto.response.LoginResDto;
import com.week07.week07.exception.ErrorCode;
import com.week07.week07.jwt.JwtUtil;
import com.week07.week07.jwt.TokenDto;
import com.week07.week07.repository.MemberRepository;
import com.week07.week07.repository.RefreshTokenRepository;
import com.week07.week07.s3.AmazonS3ResourceStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    String normalPath = "images/normal_profile.jpg";
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public GlobalResDto<?> idCheck(idCheckDto idCheckDto) {
        if (null != isPresentMember(idCheckDto.getUserid())) {
            return GlobalResDto.fail(ErrorCode.DUPLICATED_NICKNAME);
        }
        return GlobalResDto.success(null,"사용가능한 아이디 입니다.");
    }

    public GlobalResDto<?> signup(MemberReqDto memberReqDto) {
        if (null != isPresentMember(memberReqDto.getUserId())) {
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }

        if(!memberReqDto.getPw().equals(memberReqDto.getPwCheck())){
            return GlobalResDto.fail(ErrorCode.WRONG_PASSWORD);
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPw()));
        Member member = new Member(memberReqDto,normalPath);

        memberRepository.save(member);
        return GlobalResDto.success(null,"회원가입이 완료되었습니다.");
    }

    public GlobalResDto<?> login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Member member = isPresentMember(loginReqDto.getUserid());
        if(member==null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }

        if(member.validatePassword(passwordEncoder, loginReqDto.getPw())){
            return GlobalResDto.fail(ErrorCode.WRONG_PASSWORD);
        }
        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUserid());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountUserId(loginReqDto.getUserid());

        if(refreshToken.isPresent()){
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else{
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(),loginReqDto.getUserid());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response,tokenDto);
        String userImgUrl=amazonS3ResourceStorage.getimg(member.getUserImgPath());

        LoginResDto loginResDto = new LoginResDto(member,userImgUrl);
        return GlobalResDto.success(loginResDto,member.getUserName()+"님 반갑습니다.");
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String userid) {
        Optional<Member> member = memberRepository.findByUserId(userid);
        return member.orElse(null);
    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}

