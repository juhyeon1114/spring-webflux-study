package study.webflux.controller;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import study.webflux.controller.dto.UserResponse;
import study.webflux.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<UserResponse> getUserById(
            @PathVariable("userId") String userId
    ) {
        return ReactiveSecurityContextHolder
                .getContext()
                .flatMap(context -> {
                    String name = context.getAuthentication().getName();
                    if (!Objects.equals(name, userId)) {
                        return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
                    }

                    return userService.findById(userId)
                            .map(user -> new UserResponse(
                                    user.getId(),
                                    user.getName(),
                                    user.getAge(),
                                    user.getFollowCount(),
                                    user.getProfileImage()
                            )).switchIfEmpty(
                                    Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                            );
                });
    }

}
