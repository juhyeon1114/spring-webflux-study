package study.webflux.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import study.webflux.entity.common.EmptyImage;
import study.webflux.entity.common.Image;
import study.webflux.entity.common.User;
import study.webflux.repository.UserReactorRepository;

@Service
public class UserService {

    private WebClient webClient = WebClient.create("http://localhost:8081");
    private UserReactorRepository userReactorRepository = new UserReactorRepository();

    public Mono<User> findById(String userId) {
        return userReactorRepository.findById(userId)
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
                                        userEntity.getId(),
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
