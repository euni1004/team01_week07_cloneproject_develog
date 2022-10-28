package com.week07.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberReqDto {

    @NotBlank
    private String userName;
    @NotBlank
    private String userId;
    @NotBlank
    private String pw;
    @NotBlank
    private String pwCheck;
    private String intro;

    public void setEncodePwd(String encodePwd) {
        this.pw = encodePwd;
    }
}
