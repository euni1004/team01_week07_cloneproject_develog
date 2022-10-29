package com.week07.service;


import com.week07.domain.Comment;
import com.week07.domain.Post;
import com.week07.domain.Member;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.CommentReqDto;
import com.week07.dto.response.CommentResDto;
import com.week07.exception.CustomException;
import com.week07.repository.CommentRepository;
import com.week07.repository.PostRepository;
import com.week07.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public GlobalResDto<?> create(Long postId, CommentReqDto commentReqDto, Member member) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_MEMBER)
//        );

        Comment comment = new Comment(commentReqDto,member);
        commentRepository.save(comment);

        CommentResDto commentResDto = new CommentResDto(member, comment);

        return GlobalResDto.success(commentResDto,"댓글 작성 완료");

    }

    public GlobalResDto<?> delete(Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (comment.getMember().getMemberId().equals(member.getMemberId())) {
            commentRepository.deleteById(commentId);
            return GlobalResDto.success(null, "댓글이 삭제 되었습니다");
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH_COMMENTMEMBER);
        }
    }

}