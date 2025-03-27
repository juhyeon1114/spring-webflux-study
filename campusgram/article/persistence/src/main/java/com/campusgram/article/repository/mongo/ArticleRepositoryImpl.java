package com.campusgram.article.repository.mongo;

import com.campusgram.article.entity.Article;
import com.campusgram.article.repository.ArticleRepository;

import reactor.core.publisher.Mono;

public class ArticleRepositoryImpl implements ArticleRepository {

	@Override
	public Mono<Article> save(Article article) {
		// todo
		return null;
	}

}
