package com.campusgram.article.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Article {

	private String id;
	private String title;
	private String content;
	private List<ArticleThumbnail> thumbnails;
	private String creatorId;

	public static Article create(
		String title,
		String content,
		List<ArticleThumbnail> thumbnails,
		String creatorId
	) {
		return new Article(
			null,
			title,
			content,
			thumbnails,
			creatorId
		);
	}

}
