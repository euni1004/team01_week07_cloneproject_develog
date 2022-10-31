package com.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostUpdateReqDto {

    private String postTitle;

    private String postContent;

    private List<String> postTag;

    private String url;
}
