package com.week07.dto.response;

import com.week07.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostResDto {

    private Long postId;
    private List<String> postImg;
    private String postTitle;
    private String postContent;
    private String countTime;
    private String modifyPost;
    private String countDay;
    private String userImg;
    private String userName;
    private Long countLike;
    private Long countCmt;

    public static GetAllPostResDto toGetAllPostResDto(Post post, String countTime, String countDay, Long countLike, Long countCmt) {
        return new GetAllPostResDto(
                post.getPostId(),
                post.getImgUrl(),
                post.getTitle(),
                post.getContent(),
                countTime,
                post.getModifyPost(),
                countDay,
                post.getMember().getUserImgUrl(),
                post.getMember().getUserName(),
                countLike,
                countCmt
        );
    }
}
