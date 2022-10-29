package com.week07.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.util.List;


@Getter
@NoArgsConstructor
public class PostReqDto {


    private String title;

    @Lob
    private String imgUrl;

    private String content;
}
