package com.article.article.controller;

import com.article.article.exception.GeneralException;
import com.article.article.model.dto.ArticleDto;
import com.article.article.serializer.LocalDateSerializer;
import com.article.article.service.ArticleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.Collections;

@ActiveProfiles("test")
@WebMvcTest(ArticleController.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ArticleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArticleService articleService;

    Gson gson = new Gson();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void createArticle_Success() throws Exception {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

        gson = gsonBuilder.setPrettyPrinting().create();

        ArticleDto articleDto = new ArticleDto("a", "b", "c", LocalDate.parse("2022-07-21"));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/article/create")
                        .content(gson.toJson(articleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

    }

    @Test
    void createArticle_Exception() throws Exception {


        ArticleDto articleDto = new ArticleDto("aaaaaaaaaaaaaaaa", "b", "c", LocalDate.parse("2022-07-21"));

        Mockito.when(articleService.createArticle(articleDto)).thenThrow(new ConstraintViolationException("", Collections.emptySet()));
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateSerializer());

        gson = gsonBuilder.setPrettyPrinting().create();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/article/create")
                        .content(gson.toJson(articleDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();

    }

    @Test
    void listArticle_Success() throws Exception {
        int no = 0;
        int size = 1;

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/article/list")
                        .param("no", String.valueOf(no))
                        .param("size", String.valueOf(size)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getLastSevenDayArticleCount_Success() throws Exception {

        String role = "admin";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/article/count")
                        .param("role", role))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void getLastSevenDayArticleCount_Exception() throws Exception {

        String role = "aaa";

        Mockito.when(articleService.getLastSevenDayArticleCount(role)).thenThrow(GeneralException.class);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/article/count")
                        .param("role", role))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

}