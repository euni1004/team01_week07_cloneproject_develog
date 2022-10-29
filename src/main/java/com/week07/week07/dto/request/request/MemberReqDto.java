package com.week07.week07.dto.request.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    private String userid;
    private String pw;

    public void setEncodePwd(String encodePwd) {
        this.pw = encodePwd;
    }
}
