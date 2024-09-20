package com.distribuido.chat_app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChatAppApplication {

	private static final Logger logger = LoggerFactory.getLogger(ChatAppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChatAppApplication.class, args);
		logger.info("ChatAppApplication iniciada com sucesso!");
	}


	@Bean
	public CommandLineRunner runOnStartup() {
		return args -> {
			logger.info("Executando tarefas iniciais...");

		};
	}
}