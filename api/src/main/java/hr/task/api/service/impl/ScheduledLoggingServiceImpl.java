package hr.task.api.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import hr.task.api.entity.Channel;
import hr.task.api.entity.Client;
import hr.task.api.entity.CompanyLogData;
import hr.task.api.entity.Person;
import hr.task.api.model.CompanyMessageLog;
import hr.task.api.model.PerMessageLog;
import hr.task.api.repository.CompanyLogDataRepository;
import hr.task.api.repository.PerMessageLogDataRepository;
import hr.task.api.service.LoggingService;

/**
 * Logging message log state every 5 seconds.
 * 
 * @author ljuric
 *
 */
@Service
public class ScheduledLoggingServiceImpl implements LoggingService {

	private final PerMessageLogDataRepository perMessageLogDataRepository;
	private final CompanyLogDataRepository companyLogDataRepository;

	private PerMessageLog perMessageLog;
	private CompanyMessageLog companyLog;

	public ScheduledLoggingServiceImpl(PerMessageLogDataRepository perMessageLogDataRepository,
			CompanyLogDataRepository companyLogDataRepository) {
		this.perMessageLogDataRepository = perMessageLogDataRepository;
		this.companyLogDataRepository = companyLogDataRepository;
		perMessageLog = new PerMessageLog();
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
		perMessageLog.finishInterval();
		companyLog.finishInterval();
		savePerMessageIntervalLogs();
		saveCompanyIntervalLogs();
	}

	private void saveCompanyIntervalLogs() {
		CompanyLogData logData = companyLog.getIntervalData();
		if (logData != null) {
			companyLogDataRepository.save(logData);
		}
	}

	private void savePerMessageIntervalLogs() {
		perMessageLog.getIntervalKeys().stream().map(perMessageLog::getIntervalData)
				.forEach(perMessageLogDataRepository::save);
	}

	@Scheduled(fixedDelay = 5000)
	@Override
	public void logTotal() {
		perMessageLog.pointInTimeTotalState();
		companyLog.pointInTimeTotalState();
		savePerMessagePointInTimeLogs();
		saveCompanyPointInTimeLogs();
	}

	private void saveCompanyPointInTimeLogs() {
		CompanyLogData logData = companyLog.getPointInTimeData();
		if (logData != null) {
			companyLogDataRepository.save(logData);
		}
	}

	private void savePerMessagePointInTimeLogs() {
		perMessageLog.getPointInTimeTotalKeys().stream().map(perMessageLog::getPointInTimeData)
				.forEach(perMessageLogDataRepository::save);
	}

}
