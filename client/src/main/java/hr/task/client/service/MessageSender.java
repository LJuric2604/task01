package hr.task.client.service;

import hr.task.client.impl.ApiClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class MessageSender implements Runnable {

	private final ApiClient client;

	@Override
	public void run() {
		Message message = getMessage();
		final String response = this.client.sendMessage(message);
		System.out.println(response);
	}

	protected abstract Message getMessage();

}
