package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;
    @Test
    @DisplayName("특정 게시글 모든 댓글 조회")
    void findByArticleId() {
        Long articleId = 4L;
        //실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        //예상 데이터
        Article article = new Article(4L, "댓댓", "444");
        Comment a = new Comment(1L, article, "nnnnnnnnnn", "yyyyyyyy");
        Comment b = new Comment(2L, article, "kkkkkkkk", "yyyyyyyy");
        List<Comment> expected = Arrays.asList(a,b);
        //비교 검증
        assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력");
    }

    @Test
    void findByNickname() {
        String nickname = "kkkkkkkk";
        //실제 데이터
        List<Comment> comments = commentRepository.findByNickname(nickname);
        //예상 데이터
        Comment a = new Comment(2L,new Article(4L, "댓댓", "444"), nickname, "yyyyyyyy");
        List<Comment> expected = Arrays.asList(a);
        assertEquals(expected.toString(), comments.toString());
    }
}