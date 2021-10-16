package hr.task.api.service;

public interface ChannelService {

	/**
	 * Send message with the channel api.
	 * 
	 * @param uri     api uri
	 * @param text    message text
	 * @param contact to
	 */
	public void sendMessage(String uri, String text, String contact);

}
