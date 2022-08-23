package com.article.article.model.entity;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@Entity
@Table(name = "ARTICLE")
@Data
@NoArgsConstructor
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TITLE")
    @NotNull(message = "Title is not null...")
    @Size(max = 2, message = "Title should not be greater than 100 characters")
    private String title;

    @Column(name = "AUTHOR")
    @NotNull(message = "Author is not null...")
    private String author;

    @Column(name = "CONTENT")
    @NotNull(message = "Content is not null...")
    private String content;

    @Column(name = "DATE")
    @NotNull(message = "Date is not null...")
    private LocalDate date;
}
