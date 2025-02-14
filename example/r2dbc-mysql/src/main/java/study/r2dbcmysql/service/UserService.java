package study.r2dbcmysql.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import study.r2dbcmysql.common.EmptyImage;
import study.r2dbcmysql.common.Image;
import study.r2dbcmysql.common.User;
import study.r2dbcmysql.repository.UserR2dbcRepository;

@Service
public class UserService {

    private WebClient webClient = WebClient.create("http://localhost:8081");
    private UserR2dbcRepository userReactorRepository;

    public Mono<User> findById(String userId) {
        return userReactorRepository.findById(Long.valueOf(userId))
                .flatMap(userEntity -> {
                    String imageId = userEntity.getProfileImageId();

                    return webClient.get()
                            .uri("/api/images/" + imageId)
                            .retrieve()
                            .toEntity(ImageResponse.class)
                            .map(resp -> resp.getBody())
                            .map(imageResponse -> {
                                return new Image(
                                        imageResponse.id(),
                                        imageResponse.name(),
                                        imageResponse.url()
                                );
                            })
                            .switchIfEmpty(Mono.just(new EmptyImage()))
                            .map(image -> {
                                Optional<Image> profileImage = image instanceof EmptyImage
                                        ? Optional.empty()
                                        : Optional.of(image);

                                return new User(
                                        userEntity.getId().toString(),
                                        userEntity.getName(),
                                        userEntity.getAge(),
                                        profileImage,
                                        List.of(),
                                        0L
                                );
                            });
                });
    }

}
