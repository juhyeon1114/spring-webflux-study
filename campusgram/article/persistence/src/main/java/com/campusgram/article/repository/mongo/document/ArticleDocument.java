package com.campusgram.article.repository.mongo.document;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Document(collection = "articles")
@Getter
@Setter
public class ArticleDocument {

	@Id
	private ObjectId id;
	private String title;
	private String content;
	private List<String> thumbnailImageIds;
	private String creatorId;

	public ArticleDocument(
		String title,
		String content,
		List<String> thumbnailImageIds,
		String creatorId
	) {
		this.id = new ObjectId();
		this.title = title;
		this.content = content;
		this.thumbnailImageIds = thumbnailImageIds;
		this.creatorId = creatorId;
	}

}
