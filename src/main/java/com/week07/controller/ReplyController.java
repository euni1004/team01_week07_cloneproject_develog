package com.week07.controller;

import com.week07.dto.GlobalResDto;
import com.week07.dto.request.ReplyReqDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/{commentId}")
    public GlobalResDto<?> createReply(@PathVariable Long commentId,
                                       @RequestBody ReplyReqDto replyDto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.create(commentId, replyDto, userDetails.getAccount());
    }

    @DeleteMapping("/{replyId}")
    public GlobalResDto<Object> deleteRelpy(@PathVariable Long replyId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return replyService.delete(replyId, userDetails.getAccount());
    }

}