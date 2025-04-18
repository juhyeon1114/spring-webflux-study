package study.sse.repository;

import java.util.Map;
import java.util.Optional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import study.sse.entity.common.repository.ImageEntity;
import study.sse.entity.common.repository.UserEntity;

@Slf4j
public class ImageReactorRepository {

    private final Map<String, ImageEntity> imageMap;

    public ImageReactorRepository() {
        imageMap = Map.of(
                "1", new ImageEntity("1", "profileImage", "https://dailyone.com/images/1")
        );
    }

    @SneakyThrows
    public Mono<ImageEntity> findById(String id) {
        return Mono.create(sink -> {
            log.info("ImageRepository.findById: {}", id);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            var image = imageMap.get(id);
            if (image == null) {
                sink.error(new RuntimeException("image not found"));
            } else {
                sink.success(image);
            }
        });
    }

    public Mono<ImageEntity> findWithContext() {
        return Mono.deferContextual(context -> {
            Optional<UserEntity> userOptional = context.getOrEmpty("user");
            if (userOptional.isEmpty()) {
                throw new RuntimeException("user not found");
            }

            return Mono.just(userOptional.get().getProfileImageId());
        }).flatMap(this::findById);
    }

}