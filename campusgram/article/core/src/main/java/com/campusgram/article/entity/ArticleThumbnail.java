package com.campusgram.article.entity;

public record ArticleThumbnail(
	String id,
	String url,
	int width,
	int height
) {

	public static ArticleThumbnail createIdOnly(String id) {
		return new ArticleThumbnail(id, "", 0, 0);
	}

}
