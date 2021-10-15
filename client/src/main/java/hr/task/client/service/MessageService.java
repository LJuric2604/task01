package hr.task.client.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.task.client.impl.ApiClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final ApiClient client;

	@Scheduled(fixedRate = 1000)
	public void callApi() {

		Message message = new Message();
		var client = new Client();
		client.setId(1L);
		message.setClient(client);
		var person = new Person();
		person.setId(1L);
		message.setPerson(person);
		message.setText("Client test");

		this.client.sendMessage(message);

	}

}
