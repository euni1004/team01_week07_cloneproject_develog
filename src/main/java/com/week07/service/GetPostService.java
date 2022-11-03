package com.week07.service;

import com.week07.domain.*;
import com.week07.dto.GlobalResDto;
import com.week07.dto.response.GetAllPostResDto;
import com.week07.dto.response.GetOnePostDto;
import com.week07.exception.CustomException;
import com.week07.exception.ErrorCode;
import com.week07.repository.*;
import com.week07.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;

    public GlobalResDto<?> getAllPostByLike() {

        List<Post> postList = postRepository.findAll();
        List<GetAllPostResDto> getAllPostResDtoList = toGetAllPostResDto(postList);
        return GlobalResDto.success(getAllPostResDtoList, null);
    }

    public GlobalResDto<?> getAllPostByTime() {

        List<Post> postList = postRepository.findAll();
        List<GetAllPostResDto> getAllPostResDtoList = toGetAllPostResDto(postList);
        getAllPostResDtoList = getAllPostResDtoList.stream()
                .sorted(Comparator.comparing(GetAllPostResDto::getPostId).reversed()).collect(Collectors.toList());
        return GlobalResDto.success(getAllPostResDtoList, null);
    }

    //여기부터 작성
    public GlobalResDto<?> getOnePost(UserDetailsImpl userDetails, Long postId) {

        Member member = isPresentMember(userDetails);
        if (member == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_MEMBER);
        }

        Post post = isPresentPost(postId);
        if (post == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_POST);
        }

        List<String> postImg = post.getImgUrl();
        String countTime = countTime(post.getCreatedAt());
        boolean userLike = isPresentLike(member, post);
        Long countLike = countLike(post);
        List<String> tag = isPresentTag(member,post);
        GetOnePostDto getOnePostDto = GetOnePostDto.toGetOnePostDto(post, countTime, userLike, countLike, postImg,tag);

        return GlobalResDto.success(getOnePostDto, null);
    }

    public GlobalResDto<?> searchPost(String searchKeyword) {

        if (searchKeyword.length() < 2) {
            throw new CustomException(ErrorCode.KEYWORD_LENGTH_ERROR);
        }

        List<Post> postList = postRepository.findAllByTitleContainingOrContentContaining(searchKeyword, searchKeyword);

        List<GetAllPostResDto> getAllPostResDtoList = toGetAllPostResDto(postList);
        return GlobalResDto.success(getAllPostResDtoList, null);
    }

    public List<GetAllPostResDto> toGetAllPostResDto(List<Post> postList) {
        List<GetAllPostResDto> getAllPostResDtoList = new ArrayList<>();

        for (Post post : postList) {
            String countTime = countTime(post.getCreatedAt());
            String countDay = countDay(post.getCreatedAt());
            Long countLike = countLike(post);
            Long countCmt = countCmt(post);
            GetAllPostResDto getAllPostResDto = GetAllPostResDto.toGetAllPostResDto(post, countTime, countDay, countLike, countCmt);
            getAllPostResDtoList.add(getAllPostResDto);
        }

        //좋아요순으로 내림차순 정렬
        getAllPostResDtoList = getAllPostResDtoList.stream()
                .sorted(Comparator.comparing(GetAllPostResDto::getCountLike).reversed()).collect(Collectors.toList());

        return getAllPostResDtoList;
    }

    public Member isPresentMember(UserDetailsImpl userDetails) {
        Optional<Member> member = memberRepository.findById(userDetails.getAccount().getMemberId());
        return member.orElse(null);
    }

    public Post isPresentPost(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.orElse(null);
    }

    public boolean isPresentLike(Member member, Post post) {
        return postLikeRepository.findByMemberAndPost(member, post).isPresent();
    }

    public List<String> isPresentTag(Member member, Post post){
        List<Tag> tags= tagRepository.findAllByMemberAndPost(member,post);
        List<String> stringTag = new ArrayList<>();
        for(Tag tag : tags){
            String string = tag.getTagName();
            stringTag.add(string);
        }
        return stringTag;
    }

    public Long countLike(Post post) {
        List<PostLike> postLikes = postLikeRepository.findByPost(post);
        return (long) postLikes.size();
    }

    public Long countCmt(Post post) {
        List<Comment> commentList = commentRepository.findByPost(post);
        return (long) commentList.size();
    }

    //시간 계산
    public String countTime(LocalDateTime localDateTime) {
        String countTime = "";

        LocalDateTime now = LocalDateTime.now();//현재날짜 시간
        LocalDate nowDate = now.toLocalDate();//현재 날짜
        LocalTime nowTime = now.toLocalTime();//현재 시간

        LocalDate postDate = localDateTime.toLocalDate();//포스팅 날짜
        LocalTime postTime = localDateTime.toLocalTime();//포스팅 시간

        Period period = Period.between(postDate, nowDate);

        Duration duration = Duration.between(postTime, nowTime);
        long betweenTime = duration.getSeconds();

        if (period.getDays() < 1) {
            if (betweenTime <= 60) {
                countTime = "약 1분 전";
            } else if (betweenTime <= 6000) {
                countTime = "약 "+(betweenTime / 60) + "분 전";
            } else if (betweenTime <= 86400) {
                countTime = "약 "+(betweenTime / 60 / 60) + "시간 전";
            }
        } else if (period.getDays() < 7) {
            countTime = "약 "+period.getDays() + "일 전";
        } else {
            countTime = localDateTime.format(DateTimeFormatter.ofPattern("MM월 dd일 HH시 mm분"));
        }

        return countTime;
    }

    public String countDay(LocalDateTime localDateTime) {
        String countDay = "";

        LocalDateTime now = LocalDateTime.now();//현재날짜 시간
        LocalDate nowDate = now.toLocalDate();//현재 날짜

        LocalDate postDate = localDateTime.toLocalDate();//포스팅 날짜

        Period period = Period.between(postDate, nowDate);

        if (period.getDays() == 0) {
            countDay = "오늘";
        } else if (period.getDays() >= 1 && period.getDays() <= 7) {
            countDay = "일주일전";
        } else if (period.getDays() > 7 && period.getDays() <= 14) {
            countDay = "이주일전";
        } else {
            countDay = localDateTime.format(DateTimeFormatter.ofPattern("오래전"));
        }

        return countDay;
    }


}
