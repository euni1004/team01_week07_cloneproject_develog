package com.week07.week07.dto;

import com.week07.week07.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResDto<T> {

    private int status;
    private T data;
    private T msg;


    public static <T> GlobalResDto<Object> success(T data,String msg) {
        return new GlobalResDto<>(200,data,msg);
    }

    public static GlobalResDto<String> fail(ErrorCode errorCode) {
        return new GlobalResDto<>(errorCode.getStatus(),errorCode.getCode(),errorCode.getMessage());
    }

}
