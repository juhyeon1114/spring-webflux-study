package com.campusgram.article.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.usecase.CreateArticleUsecase;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@Slf4j
@AutoConfigureWebTestClient
@WebFluxTest(
        controllers = {ArticleController.class}
)
class ArticleControllerTest {

    @MockitoBean
    CreateArticleUsecase createArticleUsecase;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void contextLoads() {
    }

    @Test
    void createArticle() {
        // given
        var id = "abcd";
        var title = "title1";
        var content = "content1";
        var creatorId = "1234";
        var thumbnailImageIds = Stream.of("1", "2", "3").map(ArticleThumbnail::createIdOnly).toList();
        Article article = new Article(
                id,
                title,
                content,
                thumbnailImageIds,
                creatorId
        );

        when(createArticleUsecase.execute(any()))
                .thenReturn(Mono.just(article));

        // when
        var body = "{\"title\":\"title1\",\"content\":\"content1\",\"thumbnailImageIds\":[\"1\",\"2\",\"3\"]}";
        EntityExchangeResult<byte[]> result = webTestClient.post()
                .uri("/api/v1/articles")
                .header("Content-Type", "application/json")
                .header("X-User-Id", creatorId)
                .bodyValue(body)
                .exchange()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.title").isEqualTo(title)
                .jsonPath("$.content").isEqualTo(content)
                .jsonPath("$.creatorId").isEqualTo(creatorId)
                .returnResult();

        // then
        log.info(Arrays.toString(result.getResponseBody()));
    }

}
