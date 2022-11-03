package com.week07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReqDtoPocket {

    private List<MyPageReqDto> myPageReqDto;
    private List<String> tag;
}
