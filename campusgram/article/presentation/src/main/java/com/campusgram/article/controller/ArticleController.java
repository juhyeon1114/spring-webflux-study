package com.campusgram.article.controller;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.usecase.CreateArticleUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final CreateArticleUsecase createArticleUsecase;

    @PostMapping
    Mono<ArticleResponse> createArticle(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CreateArticleRequest createArticleRequest
    ) {
        var input = new CreateArticleUsecase.Input(
                createArticleRequest.title(),
                createArticleRequest.content(),
                createArticleRequest.thumbnailImageIds(),
                userId
        );
        return createArticleUsecase.execute(input)
                .map(this::fromEntity);
    }

    private ArticleResponse fromEntity(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getThumbnails().stream().map(this::fromEntity).toList(),
                article.getCreatorId()
        );
    }

    private ThumbnailResponse fromEntity(ArticleThumbnail thumbnail) {
        return new ThumbnailResponse(
                thumbnail.id(),
                thumbnail.url(),
                thumbnail.width(),
                thumbnail.height()
        );
    }

}
