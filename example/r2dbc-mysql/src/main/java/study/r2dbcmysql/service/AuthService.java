package study.r2dbcmysql.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private static final Map<String, String> tokenUserIdMap = Map.of(
            "abcd", "1234"
    );

    public Mono<String> getNameByToken(String token) {
        return Mono.justOrEmpty(tokenUserIdMap.get(token));
    }

}
