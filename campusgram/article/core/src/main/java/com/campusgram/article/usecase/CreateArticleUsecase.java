package com.campusgram.article.usecase;

import java.util.List;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.repository.ArticleRepository;

import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Named
public class CreateArticleUsecase {

	private final ArticleRepository articleRepository;

	public record Input(
		String title,
		String content,
		List<String> thumbnailImageIds,
		String creatorId
	) {

	}

	public Mono<Article> execute(Input input) {
		var thumbnails = input.thumbnailImageIds.stream()
			.map(ArticleThumbnail::createIdOnly)
			.toList();

		return articleRepository.save(Article.create(
			input.title,
			input.content,
			thumbnails,
			input.creatorId
		));
	}

}
