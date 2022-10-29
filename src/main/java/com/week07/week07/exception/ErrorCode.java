package com.week07.week07.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //에러코드 쓰는곳
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND.value(),"M001", "유저가 존재하지 않습니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST.value(),"M002","아이디를 사용하는 유저가 존재합니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST.value(),"M003","비밀번호가 틀렸습니다."),

    WRONG_MEMBER(HttpStatus.BAD_REQUEST.value(),"P001","작성자가 아닙니다");


    private final int status;
    private final String code;
    private final String message;
}
