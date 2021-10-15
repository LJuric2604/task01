package hr.task.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hr.task.client.service.Message;

@Component
public class ApiClient {
	
	private final RestTemplate restTemplate;
	
	@Value("${api.client.root.path}")
	private String rootPath;
	
	@Value("${api.client.messages.path}")
	private String messagesPath;
	
	public ApiClient() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		restTemplate = builder.rootUri(rootPath).build();
	}
	
	public void sendMessage(Message message) {
		restTemplate.postForEntity(messagesPath, message, Void.class);
	}

}
