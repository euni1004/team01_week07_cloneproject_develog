package com.week07.service;


import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.PostReqDto;
import com.week07.dto.request.PostUpdateReqDto;
import com.week07.exception.ErrorCode;
import com.week07.repository.MemberRepository;
import com.week07.repository.PostRepository;
import com.week07.s3.AmazonS3ResourceStorage;
import com.week07.s3.MultipartUtil;
import com.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.MetaMessage;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    //포스트 작성
    public GlobalResDto<?> createPost(PostReqDto postReqDto,
                                      UserDetailsImpl userDetails,
                                      MultipartFile multipartFile){
        Member member = isPresentMember(userDetails);
        if(member == null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }

        if(postReqDto.getPostTitle().isEmpty()){
            return GlobalResDto.fail(ErrorCode.MUST_HAVE_TITLE);
        }

        String path;
        if(multipartFile==null){
            path="";
        }else{
            path = MultipartUtil.createPath(MultipartUtil.createFileId(),MultipartUtil.getFormat(multipartFile.getContentType()));
            amazonS3ResourceStorage.store(path,multipartFile);
        }

        Post post = new Post(postReqDto,member,path);
        postRepository.save(post);

        return GlobalResDto.success(null,"게시물 등록");
    }

    @Transactional
    public GlobalResDto<?> updatePost(PostUpdateReqDto postUpdateReqDto, UserDetailsImpl userDetails, MultipartFile multipartFile, Long postId){
        Post post = isPresentPost(postId);
        if(post==null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POST);
        }

        if(!post.getMember().getUserId().equals(userDetails.getAccount().getUserId())){
            return GlobalResDto.fail(ErrorCode.NO_PERMISSION_CHANGE);
        }

        post.update(postUpdateReqDto);
        postRepository.save(post);
        return GlobalResDto.success(null,"게시물 수정");
    }

    @Transactional
    public GlobalResDto<?> deletePost(Long postId, UserDetailsImpl userDetails){
        Member member = isPresentMember(userDetails);
        if(member == null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }

        Post post = isPresentPost(postId);
        if(post ==null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_POST);
        }

        if(!member.getMemberId().equals(post.getMember().getMemberId())){
            return GlobalResDto.fail(ErrorCode.NO_PERMISSION_DELETE);
        }

        if(post.getImgUrlPath()!=null){
            amazonS3ResourceStorage.delimg(post.getImgUrlPath());
        }

        postRepository.deleteById(postId);
        return GlobalResDto.success(null,"삭제완료");
    }

    public Member isPresentMember(UserDetailsImpl userDetails){
        Optional<Member> member = memberRepository.findById(userDetails.getAccount().getMemberId());
        return member.orElse(null);
    }

    public Post isPresentPost(Long postId){
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }
}
