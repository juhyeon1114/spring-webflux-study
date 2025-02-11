package study.sse.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import study.sse.entity.common.Image;
import study.sse.repository.ImageReactorRepository;

@Service
public class ImageService {

    private ImageReactorRepository imageReactorRepository = new ImageReactorRepository();

    public Mono<Image> getImageById(String imageId) {
        return imageReactorRepository.findById(imageId)
                .map(imageEntity -> new Image(
                        imageEntity.getId(),
                        imageEntity.getName(),
                        imageEntity.getUrl()
                ));
    }

}
