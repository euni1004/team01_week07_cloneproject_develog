package com.week07.week07.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostUpdateReqDto {

    private String postContent;

    private List<String> postTag;
}
