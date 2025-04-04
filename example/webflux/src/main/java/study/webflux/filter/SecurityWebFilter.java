package study.webflux.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import study.webflux.auth.IamAuthentication;
import study.webflux.service.AuthService;

@Component
@RequiredArgsConstructor
public class SecurityWebFilter implements WebFilter {

    private final AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        String iam = exchange.getRequest().getHeaders().getFirst("X-I-AM");
        if (iam == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return authService.getNameByToken(iam)
                .map(IamAuthentication::new)
                .flatMap(authentication -> chain.filter(exchange)
                        .contextWrite(context -> {
                            Context newContext = ReactiveSecurityContextHolder
                                    .withAuthentication(authentication);
                            return context.putAll(newContext);
                        })
                )
                .switchIfEmpty(Mono.defer(() -> {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }));
    }

}
