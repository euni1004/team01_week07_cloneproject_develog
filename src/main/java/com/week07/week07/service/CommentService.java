package com.week07.week07.service;


import com.week07.week07.domain.Comment;
import com.week07.week07.domain.Member;
import com.week07.week07.dto.request.GlobalResDto;
import com.week07.week07.dto.request.request.CommentReqDto;
import com.week07.week07.exception.CustomException;
import com.week07.week07.exception.ErrorCode;
import com.week07.week07.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    public GlobalResDto<?> create(Long postId, CommentReqDto commentReqDto, Member account) {
        //        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException("게시글을 찾을수가 없습니다.")
//        );

        Comment comment = new Comment(commentReqDto,account);
        commentRepository.save(comment);

        return GlobalResDto.success(null,"댓글 작성 완료");

    }

    public GlobalResDto<?> delete(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (comment.getMember().getMemberId().equals(member.getMemberId())) {
            commentRepository.deleteById(commentId);
            return GlobalResDto.success(null, "댓글이 삭제 되었습니다");
        } else {
            throw new CustomException(ErrorCode.NOT_FOUND_COMMWNTMEMBER);
        }
    }

}