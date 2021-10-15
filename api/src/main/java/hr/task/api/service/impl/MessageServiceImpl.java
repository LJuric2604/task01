package hr.task.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.Person;
import hr.task.api.model.Message;
import hr.task.api.repository.ClientRepository;
import hr.task.api.repository.PersonRepository;
import hr.task.api.service.ChannelService;
import hr.task.api.service.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final ClientRepository clientRepository;
	private final PersonRepository personRepository;
	private final ChannelService channelService;

	@Transactional
	@Override
	public String send(Message message) {
		Client client = clientRepository.findById(message.getClient().getId()).get();
		Person person = personRepository.findById(message.getPerson().getId()).get();
		Channel minimumCostChannel = person.getMinimumCostChannel();

		channelService.sendMessage(minimumCostChannel.getUri(), message.getText(), person.getContactNumber());

		return "Sent with " + minimumCostChannel.getName();
	}
}
