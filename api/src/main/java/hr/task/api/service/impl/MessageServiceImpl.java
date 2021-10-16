package hr.task.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.Person;
import hr.task.api.exception.MissingClientException;
import hr.task.api.exception.MissingPersonException;
import hr.task.api.model.MessageReq;
import hr.task.api.repository.ClientRepository;
import hr.task.api.repository.PersonRepository;
import hr.task.api.service.ChannelService;
import hr.task.api.service.LoggingService;
import hr.task.api.service.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final ClientRepository clientRepository;
	private final PersonRepository personRepository;
	private final ChannelService channelService;
	private final LoggingService loggingService;

	@Transactional
	@Override
	public String send(MessageReq message) {
		Client client = clientRepository.findByName(message.getClient()).orElseThrow(MissingClientException::new);
		Person person = personRepository.findByName(message.getPerson()).orElseThrow(MissingPersonException::new);
		Channel minimumCostChannel = person.getMinimumCostChannel();

		channelService.sendMessage(minimumCostChannel.getUri(), message.getText(), person.getContactNumber());
		loggingService.updateMessageLog(client, person, minimumCostChannel);

		return "Sent with " + minimumCostChannel.getName();
	}
}
