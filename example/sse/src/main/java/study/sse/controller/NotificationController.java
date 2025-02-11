package study.sse.controller;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import study.sse.service.NotificationService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private static AtomicInteger lastEventId = new AtomicInteger(1);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE) // sse를 사용하기 위한 헤더
    public Flux<ServerSentEvent> getNotifications() {
        return notificationService.getMessageFromSink()
                .map(message -> {
                    int lastEventId = NotificationController.lastEventId.getAndIncrement();
                    return ServerSentEvent.builder(message)
                            .event("notification")
                            .id(String.valueOf(lastEventId))
                            .comment("sse message comment")
                            .build();
                });
//        return Flux.interval(Duration.ofMillis(1000))
//                .map(v -> "hello world");
    }

    @PostMapping
    public Mono<String> addNotification(@RequestBody Event event) {
        String notificationMessage = event.type() + " : " + event.message();
        notificationService.tryEmitNext(notificationMessage);
        return Mono.just("ok");
    }

}
