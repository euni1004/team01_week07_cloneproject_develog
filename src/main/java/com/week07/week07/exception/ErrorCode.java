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

    //post
    MUST_HAVE_TITLE(HttpStatus.BAD_REQUEST.value(), "P001","제목은 반드시 입력해야합니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND.value(), "P002","포스트가 존재하지 않습니다."),
    NO_PERMISSION_CHANGE(HttpStatus.BAD_REQUEST.value(), "P003","자신이 작성한 포스트만 수정가능합니다."),
    NO_PERMISSION_DELETE(HttpStatus.BAD_REQUEST.value(), "P004","자신이 작성한 포스트만 삭제가능합니다"),

    // Comment
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND.value(), "C001", "해당 댓글을 찾을 수 없습니다"),
    NOT_FOUND_COMMWNTMEMBER(HttpStatus.BAD_REQUEST.value(), "C002","해당 댓글 작성자가 아닙니다");


    private final int status;
    private final String code;
    private final String message;
}
