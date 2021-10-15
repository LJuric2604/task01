package hr.task.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.Person;
import hr.task.api.entity.PersonChannel;
import hr.task.api.entity.PriceEntity;
import hr.task.api.model.Message;
import hr.task.api.repository.ClientRepository;
import hr.task.api.repository.PersonRepository;
import hr.task.api.service.MessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final ClientRepository clientRepository;
	private final PersonRepository personRepository;

	@Transactional
	@Override
	public String send(Message message) {
		Client client = clientRepository.findById(message.getClient().getId()).get();
		Person person = personRepository.findById(message.getPerson().getId()).get();

		Channel minimumCostChannel = person.getPersonChannels().stream().map(PersonChannel::getChannel)
				.peek(System.out::println).min(PriceEntity::compareTo).get();

		System.out.println("Client " + client.getName() + " sending message \"" + message.getText() + "\" to person "
				+ person.getName() + " via " + minimumCostChannel.getName());

		System.out.println("Channel price: " + minimumCostChannel.getPrice());
		System.out.println("Client price: " + client.getPrice());

		Integer profit = client.getPrice() - minimumCostChannel.getPrice();
		System.out.println("Profit: " + profit);

		return "Message sent via " + minimumCostChannel.getName();
	}

}
