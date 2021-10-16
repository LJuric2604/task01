package hr.task.api.service.impl;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.Person;
import hr.task.api.model.CompanyMessageLog;
import hr.task.api.model.CounterMessageLog;
import hr.task.api.model.MessageLog;
import hr.task.api.service.LoggingService;

/**
 * Logging message log state every 5 seconds.
 * 
 * @author ljuric
 *
 */
@Service
public class ScheduledLoggingServiceImpl implements LoggingService {

	private CounterMessageLog perMessageLog;
	private CompanyMessageLog companyLog;

	public ScheduledLoggingServiceImpl() {
		perMessageLog = new CounterMessageLog();
		companyLog = new CompanyMessageLog();
	}

	@Override
	public void updateMessageLog(Client client, Person person, Channel channel) {
		perMessageLog.increment(person.getName());
		perMessageLog.increment(client.getName());
		perMessageLog.increment(channel.getName());
		companyLog.updateRevenue(client.getPrice());
		companyLog.updateCost(channel.getPrice());
		companyLog.updateProfit(client.getPrice(), channel.getPrice());
	}

	@Scheduled(fixedDelay = 5000)
	@Override
	public void logInterval() {
		finishInterval(perMessageLog);
		finishInterval(companyLog);
	}

	private void finishInterval(MessageLog messageLog) {
		messageLog.finishInterval();
		for (String key : messageLog.getIntervalKeys()) {
			Long value = messageLog.getIntervalValue(key);
			logData(key, value);
		}
	}

	@Scheduled(fixedDelay = 5000)
	@Override
	public void logTotal() {
		currentTotal(perMessageLog);
		currentTotal(companyLog);
	}

	private void currentTotal(MessageLog messageLog) {
		messageLog.pointInTimeTotalState();
		for (String key : messageLog.getPointInTimeTotalKeys()) {
			Long value = messageLog.getPointInTimeTotalValue(key);
			logData("Total " + key, value);
		}
	}

	private void logData(String key, Long value) {
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now + "\t" + key + " = " + value);
	}

}
