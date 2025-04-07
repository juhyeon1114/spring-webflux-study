package com.campusgram.article.repository.mongo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.campusgram.article.common.image.ImageClient;
import com.campusgram.article.entity.Article;
import com.campusgram.article.entity.ArticleThumbnail;
import com.campusgram.article.repository.ArticleRepository;
import com.campusgram.article.repository.mongo.document.ArticleDocument;
import com.campusgram.article.repository.mongo.repository.ArticleMongoRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ArticleRepositoryImpl implements ArticleRepository {

	private final ArticleMongoRepository articleMongoRepository;
	private final ImageClient imageClient;

	@Override
	public Mono<Article> save(Article article) {
		var documentToSave = fromEntity(article);
		return articleMongoRepository.save(documentToSave)
			.flatMap(articleDocument -> getThumbnailsByIds(articleDocument.getThumbnailImageIds())
				.collectList()
				.map(thumbnailsByIds -> fromDocument(articleDocument, thumbnailsByIds)));
	}

	private Flux<ArticleThumbnail> getThumbnailsByIds(List<String> ids) {
		return imageClient.getImagesByIds(ids)
			.map(resp -> new ArticleThumbnail(String.valueOf(resp.id()), resp.url(), resp.width(), resp.height()));
	}

	private ArticleDocument fromEntity(Article article) {
		return new ArticleDocument(
			article.getTitle(),
			article.getContent(),
			article.getThumbnails().stream().map(ArticleThumbnail::id).toList(),
			article.getCreatorId()
		);
	}

	private Article fromDocument(
		ArticleDocument articleDocument,
		List<ArticleThumbnail> thumbnails
	) {
		return new Article(
			articleDocument.getId().toHexString(),
			articleDocument.getTitle(),
			articleDocument.getContent(),
			thumbnails,
			articleDocument.getCreatorId()
		);
	}

}
