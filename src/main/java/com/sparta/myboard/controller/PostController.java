package com.sparta.myboard.controller;


import com.sparta.myboard.dto.*;
import com.sparta.myboard.entity.Post;
import com.sparta.myboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/posts") // 전체 게시글 목록 조회
    public List<PostResponseDto> getPosts() { //#1 메서드 실행
        return postService.getPosts(); //#8 서비스가 리턴한 데이터를 클라이언트에게 리턴한다.
    }

    @PostMapping("/api/post") // 게시글 작성
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest httpServletRequest){
        return postService.creatPost(requestDto, httpServletRequest);
    }

    @GetMapping("/api/post/{id}") // 선택한 게시글 조회
    //@PathVariable => {id}에 들어오는 값을 Long id에 담아줌 (받을데이터가 1개일때)
    //@requestBody => json 형식 (받을 데이터가 여러개일때)
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    @PutMapping("/api/post/{id}") //선택한 게시글 수정
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto, HttpServletRequest httpServletRequest){
        return postService.updatePost(id, requestDto, httpServletRequest);
    }

    @DeleteMapping("/api/post/{id}") //선택한 게시글 삭제
    public PostDeleteResponseDto deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return postService.deletePost(id, httpServletRequest);
    }
}
