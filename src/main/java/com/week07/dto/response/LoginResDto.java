package com.week07.dto.response;

import com.week07.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {

    private String userId;
    private String userImgUrl;
    private String intro;

    public LoginResDto(Member member, String userImgUrl) {
        this.userId = member.getUserId();
        this.userImgUrl = userImgUrl;
        this.intro = member.getIntro();
    }
}
