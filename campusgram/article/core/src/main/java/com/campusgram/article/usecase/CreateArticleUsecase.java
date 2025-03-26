package com.campusgram.article.usecase;

import java.util.List;

import com.campusgram.article.entity.Article;

import jakarta.inject.Named;
import reactor.core.publisher.Mono;

@Named
public class CreateArticleUsecase {

	public record Input(
		String title,
		String content,
		List<String> thumbnailImageIds,
		String creatorId
	) {

	}

	public Mono<Article> execute(Input input) {
		// todo
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
