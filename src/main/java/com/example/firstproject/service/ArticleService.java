package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    //GET
    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }
    //POST
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }
    //PATCH
    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id: {}",article.getId());
        Article target = articleRepository.findById(id).orElse(null);
        log.info("title : {}, title : {}", article.getTitle(), target.getTitle());
        if(target == null || id != article.getId()) {
            return null;
        }
        target.patch(article);
        return articleRepository.save(target);
    }
    //DELETE
    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //dto 묶음 엔티티 묶음 변환
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //엔티티 묶음을 db저장
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //예외 발생
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));
        return articleList;
    }
}
