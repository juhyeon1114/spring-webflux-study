package com.campusgram.article.repository.mongo.repository;

import static org.hibernate.validator.internal.util.Contracts.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.campusgram.article.repository.mongo.document.ArticleDocument;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@Testcontainers
class ArticleMongoRepositoryTest {

	@Autowired
	private ArticleMongoRepository articleMongoRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.19")
		.withEnv("MONGO_INITDB_DATABASE", "campus")
		.withExposedPorts(27017)
		.withSharding();

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		String uri = mongoDBContainer.getReplicaSetUrl("campus");
		registry.add("spring.data.mongodb.uri", () -> uri);
	}

	@Test
	void saveArticle() {
		// given
		var title = "Test Title";
		var content = "Test Content";
		var thumbnailImageIds = List.of("1", "2");
		var creatorId = "1234";

		// when
		ArticleDocument articleToSave = new ArticleDocument(
			title,
			content,
			thumbnailImageIds,
			creatorId
		);
		Mono<ArticleDocument> result = articleMongoRepository.save(articleToSave);

		// then
		StepVerifier.create(result)
			.assertNext(articleDocument -> {
				assertNotNull(articleDocument.getId());
			})
			.verifyComplete();
	}

}
