package com.sky2dev.day22.websocket;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.task.scheduling.enabled=false",
                "app.scheduling.enabled=false"
        })
class WebSocketConfigurationIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Test
    void shouldAcceptWebSocketHandshakeAndStompConnect() throws Exception {
        WebSocketStompClient client = new WebSocketStompClient(new StandardWebSocketClient());
        StompSession session = client
                .connectAsync("ws://localhost:" + port + "/ws-monitoring", new TestSessionHandler())
                .get(5, TimeUnit.SECONDS);

        assertThat(session.isConnected()).isTrue();
        session.disconnect();
        client.stop();
    }

    private static class TestSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers,
                byte[] payload, Throwable exception) {
            throw new IllegalStateException("WebSocket STOMP error", exception);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            throw new IllegalStateException("WebSocket transport error", exception);
        }
    }
}
