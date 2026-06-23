package com.sky2dev.day22.websocket;

import static org.assertj.core.api.Assertions.assertThat;

import com.sky2dev.day22.dto.AlarmResponse;
import com.sky2dev.day22.dto.TelemetryResponse;
import com.sky2dev.day22.service.AlarmService;
import com.sky2dev.day22.service.TelemetryService;
import com.sky2dev.day22.service.WebSocketBroadcastService;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.task.scheduling.enabled=false",
                "app.scheduling.enabled=false"
        })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class WebSocketBroadcastIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TelemetryService telemetryService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private WebSocketBroadcastService broadcastService;

    private WebSocketStompClient client;
    private StompSession session;

    @AfterEach
    void tearDown() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
        if (client != null) {
            client.stop();
        }
    }

    @Test
    void shouldReceiveTelemetryAndAlarmBroadcasts() throws Exception {
        client = new WebSocketStompClient(new StandardWebSocketClient());
        client.setMessageConverter(new MappingJackson2MessageConverter());

        session = client.connectAsync("ws://localhost:" + port + "/ws-monitoring", new org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter() {
        }).get(5, TimeUnit.SECONDS);

        BlockingQueue<Map<String, Object>> telemetryQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Map<String, Object>> alarmQueue = new LinkedBlockingQueue<>();

        session.subscribe("/topic/telemetry", new MapFrameHandler(telemetryQueue));
        session.subscribe("/topic/alarms", new MapFrameHandler(alarmQueue));

        List<TelemetryResponse> telemetryPayload = telemetryService.generateTelemetryBatch();
        List<AlarmResponse> alarmPayload = alarmService.evaluateAndGenerateAlarms();
        broadcastService.publish("/topic/telemetry", "TELEMETRY STREAM ACTIVE", "Telemetry test broadcast",
                telemetryPayload);
        if (!alarmPayload.isEmpty()) {
            broadcastService.publish("/topic/alarms", "ALARM BROADCASTED", "Alarm test broadcast", alarmPayload);
        }

        Map<String, Object> telemetry = telemetryQueue.poll(5, TimeUnit.SECONDS);
        assertThat(telemetry).isNotNull();
        assertThat(telemetry.get("marker")).isEqualTo("TELEMETRY STREAM ACTIVE");

        Map<String, Object> alarm = alarmQueue.poll(5, TimeUnit.SECONDS);
        assertThat(alarm).isNotNull();
        assertThat(alarm.get("marker")).isEqualTo("ALARM BROADCASTED");
    }

    static class MapFrameHandler implements StompFrameHandler {

        private final BlockingQueue<Map<String, Object>> queue;

        MapFrameHandler(BlockingQueue<Map<String, Object>> queue) {
            this.queue = queue;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return Map.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            queue.offer((Map<String, Object>) payload);
        }
    }
}
