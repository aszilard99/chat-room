package com.org.chat_room_backend.controller;

import com.org.chat_room_backend.stomp.StandardMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/global")
    @SendTo("/topic/global")
    public StandardMessage echo(StandardMessage message) {
        System.out.println("Received message: " + message.getMessage());
        return message;
    }
}
