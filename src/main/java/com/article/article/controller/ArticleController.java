package com.article.article.controller;

import com.article.article.exception.GeneralException;
import com.article.article.model.dto.ArticleDto;
import com.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


@RestController
@RequestMapping(path = "/v1/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;


    @PostMapping(path = "/create")
    ResponseEntity<Object> createArticle(@RequestBody ArticleDto articleDto) {
        try {
            return ResponseEntity.ok(articleService.createArticle(articleDto));
        } catch (ConstraintViolationException message) {
            return ResponseEntity.internalServerError().body(message.getConstraintViolations().stream().map(ConstraintViolation::getMessageTemplate).distinct());
        }
    }

    @GetMapping(path = "/list")
    ResponseEntity<List<ArticleDto>> listArticle(@RequestParam int no, @RequestParam int size) {
        return ResponseEntity.ok(articleService.listArticle(no, size));
    }

    @GetMapping(path = "/count")
    ResponseEntity<Object> getLastSevenDayArticleCount(@RequestParam String role) {
        try {
            return ResponseEntity.ok(articleService.getLastSevenDayArticleCount(role));
        } catch (GeneralException exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

}
