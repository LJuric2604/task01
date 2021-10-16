package hr.task.api.channel.api;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Client implementation for Mockup Channel Api.
 * 
 * @author ljuric
 *
 */
@Component
public class MockupChannelApi {

	private final RestTemplate restTemplate;

	public MockupChannelApi() {
		restTemplate = new RestTemplate();
	}

	/**
	 * Send the message to the mockup channel API.
	 * 
	 * @param uri
	 * @param request
	 */
	public void sendMessage(URI uri, MockupRequest request) {
		restTemplate.postForEntity(uri, request, Void.class);
	}

}
