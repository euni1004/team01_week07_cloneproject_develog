package com.week07.dto.request;

import com.week07.domain.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TagReqDto {

    private List<Tag> tag;
}
