package com.week07.week07.service;


import com.week07.week07.domain.Member;
import com.week07.week07.domain.Post;
import com.week07.week07.dto.GlobalResDto;
import com.week07.week07.dto.request.PostReqDto;
import com.week07.week07.exception.ErrorCode;
import com.week07.week07.repository.PostRepository;
import com.week07.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;



    //포스트 작성
    public GlobalResDto<?> post(PostReqDto postReqDto, UserDetailsImpl userDetails){
        Member member = userDetails.getAccount();
        Post post = new Post(postReqDto,member);
        postRepository.save(post);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return GlobalResDto.success(postResponseDto,"게시물 작성완료");
    }



    @Transactional
    public GlobalResDto<?> update(Long id, PostReqDto postReqDto, Member member){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );
        if(post.getMember().getMemberId().equals(member.getMemberId())){
            post.update(postReqDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return GlobalResDto.success(postResponseDto,"수정이 완료되었습니다");
        }else {
            return GlobalResDto.fail(ErrorCode.WRONG_MEMBER);
        }
    }

    @Transactional
    public GlobalResDto<?> deletePost(Long postId,  Member member){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );
        if(member.getMemberId().equals(post.getMember().getMemberId())){
            postRepository.deleteById(postId);
            return GlobalResDto.success(null,"삭제완료");
        }else{
            return GlobalResDto.fail(ErrorCode.WRONG_MEMBER);
        }
    }

}
