package com.campusgram.article.common.image;

public record ImageResponse(
	Long id,
	String url,
	int width,
	int height
) {
}
