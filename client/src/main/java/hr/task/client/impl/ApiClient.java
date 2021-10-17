package hr.task.client.impl;

import java.net.URI;

import org.springframework.boot.web.client.RestTemplateBuilder;
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

	private final ApiClientProperties properties;

	public ApiClient(RestTemplateBuilder builder, ApiClientProperties properties) {
		this.properties = properties;
		restTemplate = builder.basicAuthentication(properties.getUsername(), properties.getPassword()).build();
	}

	public String sendMessage(Message message) {
		URI uri = createBaseMessageURI();
		return restTemplate.postForEntity(uri, message, String.class).getBody();
	}

	private URI createBaseMessageURI() {
		final String uri = properties.getRootPath() + properties.getMessagesPath();
		return URI.create(uri);
	}

}
