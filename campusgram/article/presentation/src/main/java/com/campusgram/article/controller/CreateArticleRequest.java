package com.campusgram.article.controller;

import java.util.List;

public record CreateArticleRequest(
        String title,
        String content,
        List<String> thumbnailImageIds
) {

}
