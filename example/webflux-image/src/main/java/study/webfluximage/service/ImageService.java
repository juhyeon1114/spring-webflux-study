package study.webfluximage.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import study.webfluximage.entity.common.Image;
import study.webfluximage.repository.ImageReactorRepository;

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
