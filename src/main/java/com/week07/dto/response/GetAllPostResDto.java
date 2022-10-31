package com.week07.dto.response;

import com.week07.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPostResDto {

    private Long postId;
    private String postImg;
    private String postTitle;
    private String postContent;
    //    private List<String> tag;
    private String countTime;
    private String modifyPost;
    private String countDay;
    private String userImg;
    private String userName;
    private boolean userLike;
    private Long countLike;
    private Long countCmt;

    public static GetAllPostResDto toGetAllPostResDto(Post post, String countTime, String countDay, boolean userLike, Long countLike, Long countCmt) {
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
                userLike,
                countLike,
                countCmt
        );
    }
}
