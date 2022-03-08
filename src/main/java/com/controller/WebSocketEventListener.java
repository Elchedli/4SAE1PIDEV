package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.model.ChatMessage;
import com.model.MessageType;

@Component
public class WebSocketEventListener {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	
	@EventListener
	public void handleWebSocketConnectListener(final SessionConnectedEvent event){
		LOGGER.info("bing bong bing.We have a new cheeky little connection!!!!");
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event){
		final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		final String username = (String) headerAccessor.getSessionAttributes().get("username");
		final ChatMessage chatMessage = ChatMessage.builder()
				.type(MessageType.DISCONNECT)
				.sender(username)
				.build();
		sendingOperations.convertAndSend("/topic/public",chatMessage);
	}
}
