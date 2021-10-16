package hr.task.api.service;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.Person;

public interface LoggingService {

	/**
	 * Update message log state with sent data.
	 * 
	 * @param client
	 * @param person
	 * @param channel
	 */
	void updateMessageLog(Client client, Person person, Channel channel);

	/**
	 * Log interval state.
	 */
	void logInterval();

	/**
	 * Log current total state.
	 */
	void logTotal();

}
