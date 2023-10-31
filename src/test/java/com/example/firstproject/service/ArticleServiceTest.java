package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;
    @Test
    void index() {
        //예상 데이터
        Article a = new Article(1L, "가가가", "111");
        Article b = new Article(2L, "나나나", "222");
        Article c = new Article(3L, "다다다", "333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c));
        //실제 데이터
        List<Article> articles = articleService.index();
        //비교 및 검증
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void show_성공() {
        //예상데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가", "111");
        //실제데이터
        Article article = articleService.show(id);
        //검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패() {
        Long id = -1L;
        Article expected = null;
        Article article = articleService.show(id);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공_title_content만_있는_dto() {
        String title = "라라라";
        String content = "444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        Article article = articleService.create(dto);
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void create_실패() {
        Long id = 4L;
        String title = "라라라";
        String content = "444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        Article article = articleService.create(dto);
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_id_title_dto() {
        //예상
        Long id = 3L;
        String title = "aaaa";
        String content = "mmmm";
        Article expected = new Article(3L, title, content);
        //실제
        ArticleForm dto = new ArticleForm(null, title, content);
        Article article = articleService.update(id, dto);
        //검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void delete() {
    }
}