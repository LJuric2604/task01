package hr.task.api.channel.api;

import java.net.URI;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MockupChannelApi {

	private final RestTemplate restTemplate;

	public MockupChannelApi() {
		restTemplate = new RestTemplate();
	}

	public void sendMessage(URI uri, MockupRequest request) {
		restTemplate.postForEntity(uri, request, Void.class);
	}

}
