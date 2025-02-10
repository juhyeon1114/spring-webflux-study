package study.webfluximage.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import study.webfluximage.handler.dto.ImageResponse;
import study.webfluximage.service.ImageService;

@Component
@RequiredArgsConstructor
public class ImageHandler {

    private final ImageService imageService;

    public Mono<ServerResponse> getImageById(ServerRequest serverRequest) {
        return imageService.getImageById(serverRequest.pathVariable("imageId"))
                .map(image -> new ImageResponse(
                        image.getId(),
                        image.getName(),
                        image.getUrl()
                )).flatMap(
                        res -> ServerResponse.ok().bodyValue(res)
                ).onErrorMap(e -> new ResponseStatusException(NOT_FOUND));

    }

}
