package com.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;


@Getter
@NoArgsConstructor
public class PostReqDto {


    private String title;

    @Lob
    private String imgUrl;

    private String content;

    private String tag;
}
