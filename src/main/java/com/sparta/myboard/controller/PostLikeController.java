package com.sparta.myboard.controller;

import com.sparta.myboard.dto.MsgResponseDto;
import com.sparta.myboard.security.UserDetailsImpl;
import com.sparta.myboard.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/post/{postId}")
    public MsgResponseDto savePostLike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postLikeService.savePostLike(postId, userDetails.getUser());
    }
}
