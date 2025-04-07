package com.campusgram.article.common.image;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ImageClient {

	private final WebClient webClient;

	public Flux<ImageResponse> getImagesByIds(List<String> ids) {
		String param = String.join(",", ids);
		return webClient.get()
			.uri("/api/images?imageIds=" + param)
			.retrieve()
			.bodyToFlux(ImageResponse.class);
	}

}
