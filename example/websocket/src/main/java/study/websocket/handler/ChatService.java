package study.websocket.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

@Slf4j
@Service
public class ChatService {

    private static Map<String, Many<Chat>> chatSinkMap = new ConcurrentHashMap<>();

    public Flux<Chat> register(String iam) {
        Sinks.Many<Chat> sink = Sinks.many().unicast().onBackpressureBuffer();

        chatSinkMap.put(iam, sink);

        return sink.asFlux();
    }

    public boolean sendChat(String iam, Chat chat) {
        log.info("iam: {}, chat: {}", iam, chat);
        Sinks.Many<Chat> sink = chatSinkMap.get(iam);
        if (sink == null) {
            return false;
        }

        sink.tryEmitNext(chat);

        return true;
    }
}
