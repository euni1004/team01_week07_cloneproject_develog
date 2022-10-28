package com.week07.week07.domain;

import com.week07.week07.dto.request.MemberReqDto;
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
    @Column(name = "memberid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String pw;

    public Member(MemberReqDto memberReqDto) {
        this.userId = memberReqDto.getUserid();
        this.pw = memberReqDto.getPw();
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String pw) {
        return passwordEncoder.matches(pw, this.pw);
    }
}
