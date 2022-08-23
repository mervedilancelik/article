package com.article.article.service.impl;

import com.article.article.exception.GeneralException;
import com.article.article.model.dto.ArticleDto;
import com.article.article.model.entity.ArticleEntity;
import com.article.article.repository.ArticleRepository;
import com.article.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private static final String ROLE = "admin";


    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        ArticleEntity articleEntity = convertToEntity(articleDto);

        articleRepository.save(articleEntity);

        return convertToDto(articleEntity);
    }

    @Override
    public List<ArticleDto> listArticle(int no, int size) {

        Pageable pageable = PageRequest.of(no, size);

        Page<ArticleEntity> articleEntities = articleRepository.findAll(pageable);
        return articleEntities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Long getLastSevenDayArticleCount(String role) {

        if (role.equalsIgnoreCase(ROLE)) {

            LocalDate localDate = LocalDate.now().minusDays(7);
            return articleRepository.countAll(localDate);

        }
        throw new GeneralException("Your role not admin...");
    }

    private ArticleDto convertToDto(ArticleEntity articleEntity) {
        return modelMapper.map(articleEntity, ArticleDto.class);
    }

    private ArticleEntity convertToEntity(ArticleDto articleDto) {
        return modelMapper.map(articleDto, ArticleEntity.class);
    }

}
