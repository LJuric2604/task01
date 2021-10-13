package hr.task.api.service.impl;

import org.springframework.stereotype.Service;

import hr.task.api.model.Message;
import hr.task.api.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Override
	public void send(Message message) {
		System.out.println("sending " + message.getText());
	}

}
