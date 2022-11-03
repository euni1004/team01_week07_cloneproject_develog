package com.week07.service;

import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.domain.Tag;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.PostReqDto;
import com.week07.dto.request.PostUpdateReqDto;
import com.week07.exception.CustomException;
import com.week07.exception.ErrorCode;
import com.week07.repository.MemberRepository;
import com.week07.repository.PostRepository;
import com.week07.repository.TagRepository;
import com.week07.s3.AmazonS3ResourceStorage;
import com.week07.s3.MultipartUtil;
import com.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    //포스트 작성
    public GlobalResDto<?> createPost(PostReqDto postReqDto, UserDetailsImpl userDetails, List<MultipartFile> multipartFiles) throws IOException {
        Member member = isPresentMember(userDetails);
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        if (postReqDto.getPostTitle().isEmpty()) {
            throw new CustomException(ErrorCode.MUST_HAVE_TITLE);
        }

        if (postReqDto.getPostTag().size() > 5) {
            throw new CustomException(ErrorCode.TAG_SIZE_ERROR);
        }


        List<String> paths = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        if (multipartFiles == null) {
            paths.add("");
            urls.add("");
        } else {
            for (MultipartFile multipartFile : multipartFiles) {
                String path = MultipartUtil.createPath(MultipartUtil.createFileId(), MultipartUtil.getFormat(multipartFile.getContentType()));
                paths.add(path);
                amazonS3ResourceStorage.store(path, multipartFile);
                String url = amazonS3ResourceStorage.getimg(path);
                urls.add(url);
            }
        }

        Post post = new Post(postReqDto, member, paths, urls);

        List<String> list = postReqDto.getPostTag().stream().distinct().collect(Collectors.toList());
        List<Tag> tagList = new ArrayList<>();
        if (postReqDto.getPostTag().size() >= 1) {

            for (String tag : list) {

                Tag tag1 = new Tag(tag, member, post);
                tagRepository.save(tag1);
                tagList.add(tag1);

            }


        }

        post.updateTag(tagList);

        postRepository.save(post);

        return GlobalResDto.success(null, "게시물 등록");
    }

    @Transactional
    public GlobalResDto<?> updatePost(UserDetailsImpl userDetails, Long postId, PostUpdateReqDto postUpdateReqDto) {
        Post post = isPresentPost(postId);
        if (post == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        if (!post.getMember().getUserId().equals(userDetails.getAccount().getUserId())) {
            throw new CustomException(ErrorCode.NO_PERMISSION_CHANGE);
        }


        if (!post.getModifyPost().equals("수정됨")) {
            post.update(postUpdateReqDto, "수정됨");
        }

        postRepository.save(post);
        return GlobalResDto.success(null, "게시물 수정");
    }

    @Transactional
    public GlobalResDto<?> deletePost(Long postId, UserDetailsImpl userDetails) {
        Member member = isPresentMember(userDetails);
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        Post post = isPresentPost(postId);
        if (post == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }
        if (!member.getMemberId().equals(post.getMember().getMemberId())) {
            throw new CustomException(ErrorCode.NO_PERMISSION_DELETE);
        }
        if (!post.getImgUrlPath().isEmpty()) {
            List<String> paths = post.getImgUrlPath();
            for (String path : paths) {
                amazonS3ResourceStorage.delimg(path);
            }
        }
        postRepository.deleteById(postId);
        return GlobalResDto.success(null, "삭제완료");
    }

    public Member isPresentMember(UserDetailsImpl userDetails) {
        Optional<Member> member = memberRepository.findById(userDetails.getAccount().getMemberId());
        return member.orElse(null);
    }

    public Post isPresentPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }
}
