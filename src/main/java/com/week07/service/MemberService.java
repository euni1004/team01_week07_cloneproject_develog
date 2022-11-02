package com.week07.service;

import com.week07.domain.Member;
import com.week07.domain.RefreshToken;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.LoginReqDto;
import com.week07.dto.request.MemberReqDto;
import com.week07.dto.request.IdCheckDto;
import com.week07.dto.response.LoginResDto;
import com.week07.exception.CustomException;
import com.week07.exception.ErrorCode;
import com.week07.jwt.JwtUtil;
import com.week07.jwt.TokenDto;
import com.week07.repository.MemberRepository;
import com.week07.repository.RefreshTokenRepository;
import com.week07.s3.AmazonS3ResourceStorage;
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
    String normalUrl = "https://eunibucket.s3.ap-northeast-2.amazonaws.com/images/normal_profile.jpg";
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    public GlobalResDto<Object> idCheck(IdCheckDto idCheckDto) {
        if (null != isPresentMember(idCheckDto.getUserId())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
        }
        return GlobalResDto.success(null, "사용가능한 아이디 입니다.");
    }

    public GlobalResDto<Object> signup(MemberReqDto memberReqDto) {
        if (null != isPresentMember(memberReqDto.getUserId())) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        if (!memberReqDto.getPw().equals(memberReqDto.getPwCheck())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPw()));
        Member member = new Member(memberReqDto, normalPath, normalUrl);

        memberRepository.save(member);
        return GlobalResDto.success(null, "회원가입이 완료되었습니다.");
    }

    public GlobalResDto<?> login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Member member = memberRepository.findByUserId(loginReqDto.getUserId())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        if (member.validatePassword(passwordEncoder, loginReqDto.getPw())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }
        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getUserId());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountUserId(loginReqDto.getUserId());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getUserId());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        LoginResDto loginResDto = new LoginResDto(member, member.getUserImgUrl());
        return GlobalResDto.success(loginResDto, member.getUserName() + "님 반갑습니다.");
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

