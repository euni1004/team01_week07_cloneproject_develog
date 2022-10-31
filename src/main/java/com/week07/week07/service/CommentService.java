package com.week07.week07.service;

import com.week07.week07.domain.Comment;
import com.week07.week07.domain.Member;
import com.week07.week07.domain.Post;
import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.dto.request.CommentReqDto;
import com.week07.week07.exception.CustomException;
import com.week07.week07.exception.ErrorCode;
import com.week07.week07.repository.CommentRepository;
import com.week07.week07.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public GlobalResDto<?> create(Long postId, CommentReqDto commentReqDto, Member account) {
        Post post = isPresentPost(postId);
        if (post == null) {
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POST);
        }

        Comment comment = new Comment(commentReqDto, post, account);
        commentRepository.save(comment);

        return GlobalResDto.success(null, "댓글 작성 완료");

    }

    public GlobalResDto<?> delete(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (comment.getMember().getMemberId().equals(member.getMemberId())) {
            commentRepository.deleteById(commentId);
            return GlobalResDto.success(null, "댓글이 삭제 되었습니다");
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION_DELETE_COMMENT);
        }
    }

    public Post isPresentPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }

}
