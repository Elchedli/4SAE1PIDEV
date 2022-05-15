package com.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.model.ChatMessage;

@Controller
public class ChatController {
	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendmessage(@Payload final ChatMessage chatMessage){
		return chatMessage;
	}
	
	
	@MessageMapping("/chat.newUser")
	@SendTo("/topic/public")
	public ChatMessage newUser(@Payload final ChatMessage chatMessage,SimpMessageHeaderAccessor headerAccessor){
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		return chatMessage;
	}
}
