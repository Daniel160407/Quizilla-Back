package com.quizilla.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final MessengerEndpoint messengerEndpoint;

    @Autowired
    public WebSocketConfig(MessengerEndpoint messengerEndpoint) {
        this.messengerEndpoint = messengerEndpoint;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messengerEndpoint, "/socket").setAllowedOrigins("*");
    }
}
