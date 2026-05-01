package com.likelion.backend.api.article.service;

import com.likelion.backend.global.exception.ArticleNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("존재하지 않는 게시글 조회 시 ArticleNotFoundException이 발생하고 AOP 로깅이 트리거된다")
    void getArticleDetail_ShouldThrowException_WhenArticleNotFound() {
        // given
        Long nonExistentId = 999L;

        // when & then
        assertThrows(ArticleNotFoundException.class, () -> {
            articleService.getArticleDetail(nonExistentId);
        });
    }
}
