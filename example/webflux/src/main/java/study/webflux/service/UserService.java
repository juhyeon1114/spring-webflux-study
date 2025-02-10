package study.webflux.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import study.webflux.entity.common.User;
import study.webflux.repository.UserReactorRepository;

@Service
public class UserService {

    private UserReactorRepository userReactorRepository = new UserReactorRepository();

    public Mono<User> findById(String userId) {
        return userReactorRepository.findById(userId)
                .map(userEntity -> new User(
                        userEntity.getId(),
                        userEntity.getName(),
                        userEntity.getAge(),
                        Optional.empty(),
                        List.of(),
                        0L
                ));
    }

}
