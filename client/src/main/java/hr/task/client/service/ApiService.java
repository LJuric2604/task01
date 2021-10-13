package hr.task.client.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

	@Scheduled(fixedRate = 1000)
	public void callApi() {
		RestTemplateBuilder builder = new RestTemplateBuilder();
		RestTemplate restTemplate = builder.rootUri("http://localhost:8080/api").build();
		Message message = new Message();
		var client = new Client();
		client.setId(1L);
		message.setClient(client);
		var person = new Person();
		person.setId(1L);
		message.setPerson(person);
		message.setText("Client test");

		restTemplate.postForEntity("/messages", message, Void.class);
	}

}
