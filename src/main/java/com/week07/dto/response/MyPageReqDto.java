package com.week07.dto.response;

import com.week07.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageReqDto {

    private Long postId;
    private List<String> postImg;
    private String postTitle;
    private String postContent;
    //    private List<String> tag;
    private String countTime;
    private String modifyPost;
    private String countDay;
    private Long countLike;
    private Long countCmt;

    public static MyPageReqDto toMyPageReqDto(Post post, String countTime, String countDay, Long countLike, Long countCmt, List<String> postImg) {
        return new MyPageReqDto(
                post.getPostId(),
                postImg,
                post.getTitle(),
                post.getContent(),
                countTime,
                post.getModifyPost(),
                countDay,
                countLike,
                countCmt
        );
    }
}
