package net.bot.crypto.application.crypto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bot.crypto.application.common.service.RedisService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class CryptoWebSocketHandler implements WebSocketHandler {

    public static final String TICKER_PAYLOAD = "[{\"ticket\":\"test1123\"},{\"type\":\"ticker\",\"codes\":[\"KRW-BTC\"]},{\"format\":\"SIMPLE\"}]";
    public static final String TICKER_KEY = "TICKER_KEY";

    private final RedisService redisService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.sendMessage(new TextMessage(TICKER_PAYLOAD));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String payload = convertPayloadToString(message);
        redisService.setData(TICKER_KEY, payload);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket Error!", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("WebSocket Closed! - {}", closeStatus.getReason());
        session.close(closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String convertPayloadToString(WebSocketMessage<?> message) {
        ByteBuffer byteBuffer = (ByteBuffer) message.getPayload();
        return new String(byteBuffer.array(), StandardCharsets.UTF_8);
    }
}
