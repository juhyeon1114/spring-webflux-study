package com.campusgram.article.controller;

import java.util.List;

public record ArticleResponse(
        String id,
        String title,
        String content,
        List<ThumbnailResponse> thumbnails,
        String creatorId
) {

}
