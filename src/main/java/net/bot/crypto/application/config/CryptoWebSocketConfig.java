package net.bot.crypto.application.config;

import net.bot.crypto.application.crypto.service.CryptoWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
@EnableWebSocket
public class CryptoWebSocketConfig {

    public static final String UPBIT_WEBSOCKET_URL = "wss://api.upbit.com/websocket/v1";

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();
    }

    @Bean
    public WebSocketConnectionManager connectionManager(WebSocketClient webSocketClient, CryptoWebSocketHandler webSocketHandler) {
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(
                webSocketClient,
                webSocketHandler,
                UPBIT_WEBSOCKET_URL);
        connectionManager.setAutoStartup(true);
        return connectionManager;
    }
}
