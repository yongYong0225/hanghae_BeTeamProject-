package com.sparta.myboard.controller;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.dto.MsgResponseDto;
import com.sparta.myboard.security.UserDetailsImpl;
import com.sparta.myboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{id}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.createComment(id, commentRequestDto, userDetails.getUser()));
    }
    // 댓글 수정
    @PutMapping("/{postId}/{cmtId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId, @PathVariable Long cmtId,
                                                            @RequestBody CommentRequestDto commentRequestDto,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(postId, cmtId, commentRequestDto, userDetails.getUser()));
    }
    //댓글 삭제
    @DeleteMapping("/{postId}/{cmtId}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long postId,
                                                        @PathVariable Long cmtId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(postId, cmtId, userDetails.getUser());
        return ResponseEntity.ok(new MsgResponseDto("삭제를 성공했습니다.", HttpStatus.OK.value()));
    }
}
