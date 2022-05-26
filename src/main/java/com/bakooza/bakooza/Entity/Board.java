package com.bakooza.bakooza.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // PK

    private String title; // 제목

    @Column(name = "user_address")
    private String userAddress; // 작성자 주소

    private String content; // 내용

    @Column(name = "post_date")
    private LocalDateTime postDate = LocalDateTime.now(); // 생성일

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate; // 수정일

    private String writer; // 작성자

    private int views; // 조회수

    @Column(name = "category_id")
    private int categoryId; // 카테고리 ID

    @Column(name = "member_id")
    private Long memberId; // 작성자 ID

    @Column(name = "is_deleted")
    private int isDeleted; // 글쓴이가 글을 지웠는지

    @Builder
    public Board(long postId, String title, String userAddress, String content, String writer, int categoryId, long memberId) {
        this.postId = postId;
        this.title = title;
        this.userAddress = userAddress;
        this.content = content;
        this.writer = writer;
        this.categoryId = categoryId;
        this.memberId = memberId;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = LocalDateTime.now();
    }

    public void increaseViews(){
        this.views++;
    }
    public void delete(){
        this.isDeleted = 1;
    }
}