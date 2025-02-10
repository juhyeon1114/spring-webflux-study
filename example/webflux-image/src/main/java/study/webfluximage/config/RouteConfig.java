package study.webfluximage.config;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import study.webfluximage.handler.ImageHandler;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction router(
            ImageHandler imageHandler
    ) {
        return route()
                .path("/api", b -> b
                        .path("/images", b2 -> b2
                                .GET("/{imageId}", imageHandler::getImageById)
                        )
                ).build();
    }

}
