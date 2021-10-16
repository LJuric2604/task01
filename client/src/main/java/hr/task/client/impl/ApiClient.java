package hr.task.client.impl;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hr.task.client.service.Message;

/**
 * Client for REST API.
 * 
 * @author ljuric
 *
 */
@Component
public class ApiClient {

	private final RestTemplate restTemplate;

	@Value("${api.client.root.path}")
	private String rootPath;

	@Value("${api.client.messages.path}")
	private String messagesPath;

	public ApiClient() {
		restTemplate = new RestTemplate();
	}

	public String sendMessage(Message message) {
		URI uri = createBaseMessageURI();
		return restTemplate.postForEntity(uri, message, String.class).getBody();
	}

	private URI createBaseMessageURI() {
		final String uri = rootPath + messagesPath;
		return URI.create(uri);
	}

}
