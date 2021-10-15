package hr.task.client.service;

import hr.task.client.impl.ApiClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomMessageSender implements Runnable {

	private final ApiClient client;

	@Override
	public void run() {
		Message message = new Message();
		var client = new Client();
		client.setId(1L);
		message.setClient(client);
		var person = new Person();
		person.setId(1L);
		message.setPerson(person);
		message.setText("Client test");

		final String response = this.client.sendMessage(message);
		System.out.println(response);
	}

}
