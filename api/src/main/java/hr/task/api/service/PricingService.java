package hr.task.api.service;

public interface PricingService {

	/**
	 * Update price for channels.
	 */
	void channelPrices();

	/**
	 * Update price for clients.
	 */
	void clientPrices();

}
