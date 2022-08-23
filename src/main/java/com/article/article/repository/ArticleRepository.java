package com.article.article.repository;

import com.article.article.model.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Integer> {

    @Query("SELECT  COUNT(a) FROM ArticleEntity a where a.date>=:date")
    Long countAll(@Param("date") LocalDate date);

}
