package hr.task.api.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.PriceEntity;
import hr.task.api.repository.ChannelRepository;
import hr.task.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingServiceImpl {

	private final ClientRepository clientRepository;

	private final ChannelRepository channelRepository;

	@Transactional
	@Scheduled(fixedDelay = 5000)
	public void channelPrices() {
		List<Channel> channels = channelRepository.findAll();
		channels.forEach(this::setRandomPriceValue);
		channelRepository.saveAll(channels);
	}

	@Transactional
	@Scheduled(fixedDelay = 5000)
	public void clientPrices() {
		List<Client> clients = clientRepository.findAll();
		clients.forEach(this::setRandomPriceValue);
		clientRepository.saveAll(clients);
	}

	private void setRandomPriceValue(PriceEntity price) {
		int value = getRandomNumberInInterval(1, 5);
		price.setPrice(value);
	}

	private int getRandomNumberInInterval(int min, int max) {
		Random random = new Random();
		return random.nextInt(max+1 - min) + min;
	}

}
