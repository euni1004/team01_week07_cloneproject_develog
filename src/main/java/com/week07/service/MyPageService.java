package com.week07.service;

import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.dto.GlobalResDto;
import com.week07.dto.request.IntroReqDto;
import com.week07.dto.response.MyPageReqDto;
import com.week07.exception.ErrorCode;
import com.week07.repository.MemberRepository;
import com.week07.repository.PostRepository;
import com.week07.s3.AmazonS3ResourceStorage;
import com.week07.s3.MultipartUtil;
import com.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final AmazonS3ResourceStorage amazonS3ResourceStorage;
    private final GetPostService getPostService;

    public GlobalResDto<?> postIntro(UserDetailsImpl userDetails, IntroReqDto introReqDto) {
        Member member = isPresentMember(userDetails);
        if(member == null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }
        member.updateIntro(introReqDto.getIntro());
        memberRepository.save(member);
        return GlobalResDto.success(null,"한줄자기소개 저장이 완료되었습니다.");
    }

    public GlobalResDto<?> postImg(UserDetailsImpl userDetails, MultipartFile multipartFile) throws IOException {
        Member member = isPresentMember(userDetails);
        if(member == null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }

        String path ="";
        String url ="";
        if(Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()){
            path = "images/normal_profile.jpg";
            url = "https://eunibucket.s3.ap-northeast-2.amazonaws.com/images/normal_profile.jpg";
        }else{
            path = MultipartUtil.createPath(MultipartUtil.createFileId(), MultipartUtil.getFormat(multipartFile.getContentType()));

            if(!userDetails.getAccount().getUserImgPath().equals("images/normal_profile.jpg")){
                amazonS3ResourceStorage.delimg(member.getUserImgPath());
            }

            amazonS3ResourceStorage.store(path,multipartFile);
            url = amazonS3ResourceStorage.getimg(path);
        }

        member.updateImg(path,url);
        memberRepository.save(member);

        return GlobalResDto.success(null,"프로필 사진을 변경하였습니다.");
    }

    public GlobalResDto<?> getMyPage(String userId) {
        Member member = isPresentMember2(userId);
        if(member==null){
            return GlobalResDto.fail(ErrorCode.NOT_FOUND_MEMBER);
        }
        List<Post> postList = isPresentPost(member);
        List<MyPageReqDto> myPageReqDtos = new ArrayList<>();

        for(Post post:postList){
            String countTime = getPostService.countTime(post.getCreatedAt());
            String countDay = getPostService.countDay(post.getCreatedAt());
            Long countLike = getPostService.countLike(post);
            Long countCmt = getPostService.countCmt(post);
            List<String> postImg = post.getImgUrl();
            MyPageReqDto myPageReqDto = MyPageReqDto.toMyPageReqDto(post,countTime,countDay,countLike,countCmt,postImg);
            myPageReqDtos.add(myPageReqDto);
        }

        return GlobalResDto.success(myPageReqDtos,null);
    }

    public Member isPresentMember(UserDetailsImpl userDetails) {
        Optional<Member> member = memberRepository.findById(userDetails.getAccount().getMemberId());
        return member.orElse(null);
    }

    public Member isPresentMember2(String userId){
        Optional<Member> member = memberRepository.findByUserId(userId);
        return member.orElse(null);
    }

    public List<Post> isPresentPost(Member member){
        return postRepository.findAllByMember(member);
    }



}
