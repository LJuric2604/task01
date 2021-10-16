package hr.task.client.service;

import java.util.Random;

import hr.task.client.impl.ApiClient;

/**
 * Message sender that randomly picks client and person.
 * 
 * @author ljuric
 *
 */
public class RandomMessageSender extends MessageSender {

	private final String[] clients;
	private final String[] persons;

	public RandomMessageSender(ApiClient client) {
		super(client);
		clients = new String[] { "C1", "C2", "C3", "C4", "C5" };
		persons = new String[] { "P1", "P2", "P3", "P4", "P5" };
	}

	@Override
	protected Message getMessage() {
		String client = getRandomClient();
		String person = getRandomPerson();
		String text = client + " to " + person;

		return Message.builder().client(client).person(person).text(text).build();
	}

	private String getRandomPerson() {
		return getRandomValue(persons);
	}

	private String getRandomClient() {
		return getRandomValue(clients);
	}

	private String getRandomValue(String[] array) {
		int randomIndex = getRandomNumberInInterval(0, array.length);
		return array[randomIndex];
	}

	private int getRandomNumberInInterval(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + min;
	}

}
