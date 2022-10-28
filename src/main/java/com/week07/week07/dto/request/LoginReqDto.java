package com.week07.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank
    private String userid;
    @NotBlank
    private String pw;

    public LoginReqDto(String userid) {
        this.userid = userid;
    }

}