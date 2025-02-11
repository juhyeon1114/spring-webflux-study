package study.sse.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class NotificationService {

    private static Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

    public Flux<String> getMessageFromSink() {
        return sink.asFlux();
    }

    public void tryEmitNext(String message) {
        sink.tryEmitNext(message);
    }

}
