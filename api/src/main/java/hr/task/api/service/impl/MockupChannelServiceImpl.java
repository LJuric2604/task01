package hr.task.api.service.impl;

import java.net.URI;

import org.springframework.stereotype.Service;

import hr.task.api.channel.api.MockupChannelApi;
import hr.task.api.channel.api.MockupRequest;
import hr.task.api.service.ChannelService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MockupChannelServiceImpl implements ChannelService {

	private final MockupChannelApi clientApi;

	@Override
	public void sendMessage(String uri, String text, String contact) {
		URI pathURI = URI.create(uri);
		MockupRequest request = new MockupRequest(text, contact);
		clientApi.sendMessage(pathURI, request);
	}

}
