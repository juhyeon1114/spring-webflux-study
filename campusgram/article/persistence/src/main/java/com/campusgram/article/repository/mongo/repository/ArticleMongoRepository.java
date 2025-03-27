package com.campusgram.article.repository.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.campusgram.article.repository.mongo.document.ArticleDocument;

public interface ArticleMongoRepository extends ReactiveMongoRepository<ArticleDocument, ObjectId> {
}
