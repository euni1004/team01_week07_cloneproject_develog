package com.week07.controller;



import com.week07.dto.GlobalResDto;
import com.week07.dto.request.CommentReqDto;
import com.week07.security.UserDetailsImpl;
import com.week07.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}")
    public GlobalResDto<?> createComment(@PathVariable Long postId,
                                         @RequestBody CommentReqDto commentReqDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.create(postId, commentReqDto, userDetails.getAccount());
    }

    @DeleteMapping("/{commentId}")
    public GlobalResDto<?> deleteComment(@PathVariable Long commentId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.delete(commentId, userDetails.getAccount());
    }


}
