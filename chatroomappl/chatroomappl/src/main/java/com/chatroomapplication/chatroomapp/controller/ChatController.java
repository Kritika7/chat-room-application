package com.chatroomapplication.chatroomapp.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.chatroomapplication.chatroomapp.model.User;
import com.chatroomapplication.chatroomapp.model.WebSocketMessage;
import com.chatroomapplication.chatroomapp.repository.ChatMessageRepository;
import com.chatroomapplication.chatroomapp.repository.UserRepository;

@Controller
public class ChatController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ChatMessageRepository messageRepository;
	
	@GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }
    
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public WebSocketMessage sendMessage(@Payload WebSocketMessage message, Principal principal) {
    	User currentUser = userRepository.findByUsername(principal.getName());
    	message.setSender(currentUser);
    	messageRepository.save(message);

        return message;	
    }
    
}
