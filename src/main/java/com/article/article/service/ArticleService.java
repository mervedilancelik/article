package com.article.article.service;

import com.article.article.model.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);

    List<ArticleDto> listArticle(int no,int size);

    Long getLastSevenDayArticleCount(String role);
}
