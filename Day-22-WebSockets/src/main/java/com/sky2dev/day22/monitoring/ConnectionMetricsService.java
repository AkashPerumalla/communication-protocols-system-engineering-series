package com.sky2dev.day22.monitoring;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class ConnectionMetricsService {

    private final Set<String> activeSessions = ConcurrentHashMap.newKeySet();
    private final AtomicLong totalConnections = new AtomicLong();
    private final AtomicLong messagesSent = new AtomicLong();
    private final AtomicLong messagesReceived = new AtomicLong();
    private final AtomicLong broadcastCount = new AtomicLong();

    public void onConnect(String sessionId) {
        if (sessionId != null && activeSessions.add(sessionId)) {
            totalConnections.incrementAndGet();
        }
    }

    public void onDisconnect(String sessionId) {
        if (sessionId != null) {
            activeSessions.remove(sessionId);
        }
    }

    public void onMessageSent() {
        messagesSent.incrementAndGet();
    }

    public void onMessageReceived() {
        messagesReceived.incrementAndGet();
    }

    public void onBroadcast() {
        broadcastCount.incrementAndGet();
    }

    public long getActiveSessions() {
        return activeSessions.size();
    }

    public long getTotalConnections() {
        return totalConnections.get();
    }

    public long getMessagesSent() {
        return messagesSent.get();
    }

    public long getMessagesReceived() {
        return messagesReceived.get();
    }

    public long getBroadcastCount() {
        return broadcastCount.get();
    }
}
