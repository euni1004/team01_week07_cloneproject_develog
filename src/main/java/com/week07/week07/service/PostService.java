package com.week07.week07.service;


import com.week07.week07.domain.Member;
import com.week07.week07.domain.Post;
import com.week07.week07.dto.request.PostReqDto;
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
    public void post(PostReqDto postReqDto, UserDetailsImpl userDetails){
        Member member = userDetails.getAccount();
        Post post = new Post(postReqDto,member);
        postRepository.save(post);

    }



    @Transactional
    public void update(Long id, PostReqDto postReqDto, Member member){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );

        if(post.getMember().getMemberId().equals(member.getMemberId())){
            post.update(postReqDto);
            return new Post(post);
        }else {
            throw new RuntimeException("작성자가 아닙니다.");
        }
    }

    @Transactional
    public String deletePost(Long postId,  Member member){
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당글이 없습니다")
        );
        if(member.getMemberId().equals(post.getMember().getMemberId())){
            postRepository.deleteById(postId);
            return "삭제된 게시글 번호 : "+postId;
        }else{
            throw new RuntimeException("아이디가 다릅니다.") ;
        }
    }

}
