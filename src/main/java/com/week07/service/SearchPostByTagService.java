package com.week07.service;

import com.week07.domain.Member;
import com.week07.domain.Post;
import com.week07.domain.Tag;
import com.week07.dto.GlobalResDto;
import com.week07.dto.response.GetAllPostResDto;
import com.week07.exception.CustomException;
import com.week07.exception.ErrorCode;
import com.week07.repository.MemberRepository;
import com.week07.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPostByTagService {

    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final GetPostService getPostService;

    public GlobalResDto<?> searchMyPostByTag(String tag, String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND_MEMBER));

        List<Tag> tagList = tagRepository.findAllByMemberAndTagName(member,tag);

        List<GetAllPostResDto> getAllPostResDtoList = getAllPost(tagList);

        return GlobalResDto.success(getAllPostResDtoList,null);
    }

    public GlobalResDto<?> searchAllPostByTag(String tag) {
        List<Tag> tagList = tagRepository.findAllByTagName(tag);
        List<GetAllPostResDto> getAllPostResDtoList = getAllPost(tagList);
        return GlobalResDto.success(getAllPostResDtoList,null);
    }

    public List<GetAllPostResDto> getAllPost(List<Tag> tagList){
        List<Post> postList = new ArrayList<>();
        for(Tag tag1:tagList){
            Post post =tag1.getPost();
            postList.add(post);
        }
        return getPostService.toGetAllPostResDto(postList);
    }
}
