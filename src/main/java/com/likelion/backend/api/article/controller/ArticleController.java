package com.likelion.backend.api.article.controller;

import com.likelion.backend.api.article.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "게시판(Article)", description = "게시판 관련 API 입니다.")
@RestController
@RequestMapping( "/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;


}
