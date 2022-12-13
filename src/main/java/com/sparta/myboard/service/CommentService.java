package com.sparta.myboard.service;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.CommentResponseDto;
import com.sparta.myboard.entity.Comment;
import com.sparta.myboard.entity.Post;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.repository.CommentRepository;
import com.sparta.myboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    // private final JwtUtil jwtUtil;
    // private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public CommentResponseDto createComment(Long id, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment = commentRepository.save(new Comment(commentRequestDto, post, user));
        // int cnt = 0;
        return new CommentResponseDto(comment); // cnt
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long postId, Long cmtId, CommentRequestDto commentRequestDto, User user) {
        // DB에 게시글 저장 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        } else {
            // user 권한이 ADMIN이 아니면 아이디가 같은 유저여야만 수정이 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }
        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public CommentResponseDto deleteComment(Long postId, Long cmtId, User user) {
        // DB에 게시글 저장 확인
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        Comment comment;

        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(cmtId).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        } else {
            // user 권한이 ADMIN이 아니면 아이디가 같은 유저여야만 수정이 가능
            comment = commentRepository.findByIdAndUserId(cmtId, user.getId()).orElseThrow(
                    () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
            );
        }
        // 해당 댓글 삭제
        commentRepository.deleteById(cmtId);
        return new CommentResponseDto(comment);
    }
}




//    public List<CommentResponseDto> getComment(Long id) {
//        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
//        List<Comment> comments = commentRepository.findAllByPost(post);
//        if (comments.isEmpty()) {
//            return Collections.emptyList();
//        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication.getPrincipal() == "anonymousUser") {
//            return comments
//                    .stream()
//                    .map(comment -> CommentResponseDto.of(comment, false))
//                    .collect(Collectors.toList());
//        } else {
//            User user = userRepository.findById(Long.parseLong(authentication.getName())).orElseThrow();
//            Map<Boolean, List<Comment>> collect = comments.stream()
//                    .collect(
//                            Collectors.partitioningBy(
//                                    comment -> comment.getUser().equals(user)
//                            )
//                    );
//            List<CommentResponseDto> tCollect = collect.get(true).stream()
//                    .map(t -> CommentResponseDto.of(t,true))
//                    .collect(Collectors.toList());
//            List<CommentResponseDto> fCollect = collect.get(false).stream()
//                    .map(f -> CommentResponseDto.of(f, false))
//                    .collect(Collectors.toList());
//            return Stream
//                    .concat(tCollect.stream(), fCollect.stream())
//                    .sorted(Comparator.comparing(CommentResponseDto::getId))
//                    .collect(Collectors.toList());
//        }
//    }
//
//    @Transactional
//    public CommentResponseDto createComment(Long id, String content) {
//        User user = userRepository.findById(
//                SecurityUtil.getCurrentUserID())
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
//
//        Comment comment = Comment.builder()
//                .content(content)
//                .post(post)
//                .user(user)
//                .build();
//        return CommentResponseDto.of(commentRepository.save(comment), true);
//    }
//
//    @Transactional
//    public void deleteComment(Long id) {
//        User user = userRepository.findById(
//                SecurityUtil.getCurrentMemberID())
//                .orElseThrow(() -> new RuntimeException("로그인 하세요."));
//        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글이 없습니다."));
//        if (!comment.getUser().equals(user)) {
//            throw new RuntimeException("작성자와 로그인이 일치하지 않습니다.");
//        }
//        commentRepository.delete(comment);
//    }
//}
