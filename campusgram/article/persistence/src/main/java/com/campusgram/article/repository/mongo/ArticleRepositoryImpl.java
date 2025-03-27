package com.campusgram.article.repository.mongo;

import org.springframework.stereotype.Component;

import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.repository.ArticleRepository;
import com.campusgram.article.repository.mongo.document.ArticleDocument;
import com.campusgram.article.repository.mongo.repository.ArticleMongoRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ArticleRepositoryImpl implements ArticleRepository {

	private final ArticleMongoRepository articleMongoRepository;

	@Override
	public Mono<Article> save(Article article) {
		var documentToSave = fromEntity(article);
		return articleMongoRepository.save(documentToSave)
			.map(this::fromDocument);
	}

	private ArticleDocument fromEntity(Article article) {
		return new ArticleDocument(
			article.getTitle(),
			article.getContent(),
			article.getThumbnails().stream().map(ArticleThumbnail::id).toList(),
			article.getCreatorId()
		);
	}

	private Article fromDocument(ArticleDocument articleDocument) {
		return new Article(
			articleDocument.getId().toHexString(),
			articleDocument.getTitle(),
			articleDocument.getContent(),
			articleDocument.getThumbnailImageIds().stream()
				.map(ArticleThumbnail::createIdOnly)
				.toList(),
			articleDocument.getCreatorId()
		);
	}

}
