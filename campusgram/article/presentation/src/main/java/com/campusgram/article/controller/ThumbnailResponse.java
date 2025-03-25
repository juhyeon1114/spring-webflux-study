package com.campusgram.article.controller;

public record ThumbnailResponse(
        String id,
        String url,
        int width,
        int height
) {

}
