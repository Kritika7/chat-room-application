package com.chatroomapplication.chatroomapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatroomapplication.chatroomapp.model.WebSocketMessage;

@Repository
public interface ChatMessageRepository extends JpaRepository<WebSocketMessage, Long> {
	
	

}
