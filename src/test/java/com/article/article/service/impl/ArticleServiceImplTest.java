package com.article.article.service.impl;

import com.article.article.exception.GeneralException;
import com.article.article.model.dto.ArticleDto;
import com.article.article.model.entity.ArticleEntity;
import com.article.article.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ArticleServiceImplTest {

    @InjectMocks
    ArticleServiceImpl articleService;

    @Mock
    ArticleRepository articleRepository;

    @Mock
    private ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createArticle() {

        ArticleDto articleDto = new ArticleDto("a", "b", "c", LocalDate.parse("2022-07-21"));

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("a");
        articleEntity.setAuthor("b");
        articleEntity.setContent("c");
        articleEntity.setDate(LocalDate.parse("2022-07-21"));

        Mockito.when(modelMapper.map(articleDto, ArticleEntity.class)).thenReturn(articleEntity);

        Mockito.when(articleRepository.save(articleEntity)).thenReturn(articleEntity);

        Mockito.when(modelMapper.map(articleEntity, ArticleDto.class)).thenReturn(articleDto);

        ArticleDto result = articleService.createArticle(articleDto);

        assertEquals(articleDto, result);

    }

    @Test
    void listArticle() {

        Pageable pageable = PageRequest.of(0, 2);

        List<ArticleDto> articleDtoList = new ArrayList<>();

        ArticleDto articleDto = new ArticleDto("a", "b", "c", LocalDate.parse("2022-07-21"));

        articleDtoList.add(articleDto);

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("a");
        articleEntity.setAuthor("b");
        articleEntity.setContent("c");
        articleEntity.setDate(LocalDate.parse("2022-07-21"));

        Page<ArticleEntity> articleEntities = new PageImpl<>(Collections.singletonList(articleEntity));


        Mockito.when(articleRepository.findAll(pageable)).thenReturn(articleEntities);
        Mockito.when(modelMapper.map(articleEntity, ArticleDto.class)).thenReturn(articleDto);

        List<ArticleDto> result = articleService.listArticle(0, 2);

        assertEquals(articleDtoList, result);


    }

    @Test
    void getLastSevenDayArticleCount() {

        String role = "admin";

        LocalDate localDate = LocalDate.parse("2022-08-16");

        Mockito.when(articleRepository.countAll(localDate)).thenReturn(1L);

        Long result = articleService.getLastSevenDayArticleCount(role);

        assertEquals(1, result);

    }

    @Test
    void getLastSevenDayArticleCount_RoleNotAdmin() {

        String role = "deneme";

        assertThrows(GeneralException.class, () -> articleService.getLastSevenDayArticleCount(role));
    }

}