package com.week07.dto.response;

import com.week07.domain.Comment;
import com.week07.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetOnePostDto {

    private String postImg;
    private String postTitle;
    private String postContent;
    //    private List<String> tag;
    private String countTime;
    private String modifyPost;
    private String userImg;
    private String userName;
    private boolean userLike;
    private Long countLike;
    private List<Comment> comments;

    public static GetOnePostDto toGetOnePostDto(Post post, String countTime, boolean userLike, Long countLike) {

        return new GetOnePostDto(
                post.getImgUrl(),
                post.getTitle(),
                post.getContent(),
                countTime,
                post.getModifyPost(),
                post.getMember().getUserImgUrl(),
                post.getMember().getUserName(),
                userLike,
                countLike,
                post.getComment());
    }
}
