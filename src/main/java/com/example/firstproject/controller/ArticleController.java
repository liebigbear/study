package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm articleForm) {
        //System.out.println(articleForm.toString());
        Article article = articleForm.toEntity();
        log.info(articleForm.toString());
        Article saved = articleRepository.save(article);
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/" +saved.getId();
    }
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = "+id);
        //id 조회
        //Optional<Article> article = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        //모델에 데이터등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        //뷰 페이지 설정
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model) {
        //모든 데이터 가져오기
        List<Article> articleEntity = articleRepository.findAll();
        //모델에 데이터 등록
        model.addAttribute("articleList", articleEntity);
        //뷰 페이지 설정
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
    }
    @PostMapping("/articles/update")
    public String update(ArticleForm articleForm) {
        Article article = articleForm.toEntity();
        log.info(article.toString());
        Article target = articleRepository.findById(article.getId()).orElse(null);
        if(target != null) {
            articleRepository.save(article);
        }
        return "redirect:/articles/"+article.getId();
    }
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다.!");
        }
        return "redirect:/articles";
    }
}
