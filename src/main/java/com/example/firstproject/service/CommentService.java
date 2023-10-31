package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    //댓글 조회
    public List<CommentDto> comments(Long articleId) {
        /*List<Comment> comments = commentRepository.findByArticleId(articleId);
        //엔티디 -> dto
        List<CommentDto> dtos = new ArrayList<CommentDto>();
        for(int i = 0; i < comments.size(); i++) {
            Comment c = comments.get(i);
            CommentDto dto = CommentDto.createCommentDto(c);
            dtos.add(dto);
        }*/
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }
    //댓글 생성
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        //게시글 조회 예외
        Article article = articleRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("댓글 생성 실패! +" +
                "대상 게시글이 없습니다"));
        //댓글 엔티티
        Comment comment = Comment.createComment(dto, article);
        //댓글 엔티티 db저장
        Comment created = commentRepository.save(comment);
        //dto변환 반환
        return CommentDto.createCommentDto(created);
    }
    //댓글 수정
    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        //코멘트 가져오기
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글 수정 실패!" +
                "대상 댓글이 없습니다"));
        //댓글 수정
        target.patch(dto);
        //db저장
        Comment updated = commentRepository.save(target);
        //dto변환
        return CommentDto.createCommentDto(updated);
    }
    @Transactional
    public CommentDto delete(Long id) {
        Comment target = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패!" +
                "대상 댓글이 없습니다"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }

    //댓글 삭제
}
