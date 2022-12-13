package com.sparta.myboard.entity;

import lombok.*;

import javax.persistence.*;

@Entity // DB 테이블 역할
@Getter // 필드에 선언시 자동으로 get 메소드 생성. 클래스에서 선언시 모든 필드에 접근자와 설정자가 자동으로 생성
@Builder
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 생성
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CommentLikeId;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "comment_like_user_id")
    private User user;
}
