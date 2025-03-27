package com.campusgram.article.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.campusgram.article.entity.Article;
import com.campusgram.article.repository.ArticleRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class CreateArticleUsecaseTest {

	@InjectMocks
	CreateArticleUsecase createArticleUsecase;

	@Mock
	ArticleRepository articleRepository;

	@Test
	void happyCase() {
		// given
		var newArticleId = "1";
		var title = "title1";
		var content = "content1";
		var thumbnailImageIds = List.of("1", "2");
		var creatorId = "creator1";

		Article savedArticle = new Article(
			newArticleId,
			title,
			content,
			List.of(),
			creatorId
		);

		when(articleRepository.save(any()))
			.thenReturn(Mono.just(savedArticle));

		// when
		var input = new CreateArticleUsecase.Input(
			title,
			content,
			thumbnailImageIds,
			creatorId
		);
		Mono<Article> result = createArticleUsecase.execute(input);

		// then
		StepVerifier.create(result)
			.assertNext(article -> {
				assertEquals(newArticleId, article.getId());
				assertEquals(title, article.getTitle());
				assertEquals(content, article.getContent());
				assertEquals(creatorId, article.getCreatorId());
			})
			.verifyComplete();
	}

}
