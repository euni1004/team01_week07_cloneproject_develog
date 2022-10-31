package com.week07.service;

import com.week07.domain.Comment;
import com.week07.domain.Member;
import com.week07.domain.Reply;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.ReplyReqDto;
import com.week07.exception.CustomException;
import com.week07.exception.ErrorCode;
import com.week07.repository.CommentRepository;
import com.week07.repository.ReplyRepositoey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private  final CommentRepository commentRepository;
    private final ReplyRepositoey replyRepositoey;

    @Transactional
    public GlobalResDto<?> create(Long commentId, ReplyReqDto replyDto, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        Reply reply = new Reply(replyDto, comment, member);
        replyRepositoey.save(reply);
        return GlobalResDto.success(null,"대댓글 작성 완료");
    }

    @Transactional
    public GlobalResDto<Object> delete(Long replyId, Member member) {
        Reply reply = replyRepositoey.findById(replyId).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_COMMENT)
        );

        if (reply.getMember().getMemberId().equals(member.getMemberId())) {
            replyRepositoey.deleteById(replyId);
            return GlobalResDto.success(null, "대댓글 삭제 완료");
        } else {
            throw new CustomException(ErrorCode.NO_PERMISSION_DELETE_COMMENT);
        }
    }

}
