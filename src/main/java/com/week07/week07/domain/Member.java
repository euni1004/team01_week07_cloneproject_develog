package com.week07.week07.domain;

import com.week07.week07.dto.request.request.MemberReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Timestamped{

    @Id
    @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String pw;

    private String intro;

    private String userImgPath;

//    @ElementCollection
//    private List<String> userTag;

    public Member(MemberReqDto memberReqDto, String normalPath) {
        this.userName = memberReqDto.getUserName();
        this.userId = memberReqDto.getUserId();
        this.pw = memberReqDto.getPw();
        this.intro = memberReqDto.getIntro();
        this.userImgPath = normalPath;
    }


    public boolean validatePassword(PasswordEncoder passwordEncoder, String pw) {
        return !passwordEncoder.matches(pw, this.pw);
    }
}
