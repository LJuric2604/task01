package hr.task.api.service;

import hr.task.api.model.MessageReq;

public interface MessageService {

	/**
	 * Send message to the channel.
	 * 
	 * @param message request data
	 * @return info message
	 */
	public String send(MessageReq message);

}
