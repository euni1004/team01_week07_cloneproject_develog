package com.week07.service;

import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.domain.PostLike;
import com.week07.dto.GlobalResDto;
import com.week07.exception.ErrorCode;
import com.week07.repository.MemberRepository;
import com.week07.repository.PostLikeRepository;
import com.week07.repository.PostRepository;
import com.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public GlobalResDto<?> createPostLike(UserDetailsImpl userDetails, Long postId) {
        Member member = userDetails.getAccount();

        Post post = isPresentPost(postId);
        if (post == null) {
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POST);
        }

        PostLike postLike = new PostLike(member, post);
        postLikeRepository.save(postLike);
        return GlobalResDto.success(null, "좋아요 완료");
    }

    @Transactional
    public GlobalResDto<?> deletePostLike(UserDetailsImpl userDetails, Long postId) {
        Member member = userDetails.getAccount();

        Post post = isPresentPost(postId);

        if (post == null) {
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POST);
        }

        PostLike postLike = isPresentPostLike(member, post);
        if (postLike == null) {
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POSTLIKE);
        }

        postLikeRepository.delete(postLike);
        return GlobalResDto.success(null, "좋아요 취소ㅠㅠ");
    }


    public Post isPresentPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }

    public PostLike isPresentPostLike(Member member, Post post) {
        Optional<PostLike> postLike = postLikeRepository.findByMemberAndPost(member, post);
        return postLike.orElse(null);
    }
}
