package com.week07.domain;

import com.week07.dto.request.MemberReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(nullable = false)
    private String userImgPath;

    @Column(nullable = false)
    private String userImgUrl;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

//    @ElementCollection
//    private List<String> userTag;

    public Member(MemberReqDto memberReqDto, String normalPath, String normalUrl) {
        this.userName = memberReqDto.getUserName();
        this.userId = memberReqDto.getUserId();
        this.pw = memberReqDto.getPw();
        this.intro = memberReqDto.getIntro();
        this.userImgPath = normalPath;
        this.userImgUrl = normalUrl;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String pw) {
        return !passwordEncoder.matches(pw, this.pw);
    }

    public void updateIntro(String intro) {
        this.intro = intro;
    }

    public void updateImg(String path, String url) {
        this.userImgPath = path;
        this.userImgUrl = url;
    }
}
