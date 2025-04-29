package com.org.chat_room_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ChatRoomBackendApplication {

	public static void main(String[] args) {

		var context = SpringApplication.run(ChatRoomBackendApplication.class, args);
		System.out.println(Arrays.toString(context.getBeanDefinitionNames()));
	}

}
