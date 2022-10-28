package com.week07.week07.service;

import com.week07.week07.domain.Member;
import com.week07.week07.domain.RefreshToken;
import com.week07.week07.dto.request.LoginReqDto;
import com.week07.week07.dto.request.MemberReqDto;
import com.week07.week07.dto.request.idCheckDto;
import com.week07.week07.exception.ErrorCode;
import com.week07.week07.exception.ErrorResponse;
import com.week07.week07.jwt.JwtUtil;
import com.week07.week07.jwt.TokenDto;
import com.week07.week07.repository.MemberRepository;
import com.week07.week07.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public ResponseEntity<?> idCheck(idCheckDto idCheckDto) {
        if (null != isPresentMember(idCheckDto.getUserid())) {
            return ResponseEntity.ok(ErrorResponse.toResponseEntity(ErrorCode.DUPLICATED_NICKNAME));
        }
        return ResponseEntity.ok("사용 가능한 아이디 입니다.");
    }

    public ResponseEntity<?> signup(MemberReqDto memberReqDto) {
        if (null != isPresentMember(memberReqDto.getUserid())) {
            return ResponseEntity.ok(ErrorResponse.toResponseEntity(ErrorCode.DUPLICATED_NICKNAME));
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getUserid()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return ResponseEntity.ok(memberReqDto.getUserid()+"님 반갑습니다.");
    }

    public ResponseEntity<?> login(LoginReqDto loginReqDto, HttpServletResponse response) {
        Member member = isPresentMember(loginReqDto.getUserid());
        if(member==null){
            return ResponseEntity.ok(ErrorResponse.toResponseEntity(ErrorCode.NOT_FOUND_MEMBER));
        }
        if(!member.validatePassword(passwordEncoder,loginReqDto.getPw())){
            return ResponseEntity.ok(ErrorResponse.toResponseEntity(ErrorCode.WRONG_PASSWORD));
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
        return ResponseEntity.ok(new LoginReqDto(member.getUserid()));
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

