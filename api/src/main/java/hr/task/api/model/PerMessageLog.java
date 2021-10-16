package hr.task.api.model;

import java.util.UUID;

import hr.task.api.entity.PerMessageLogData;

public class PerMessageLog extends MessageLog {

	public void increment(String key) {
		update(key, 1);
	}

	public PerMessageLogData getIntervalData(String key) {
		final Long value = getIntervalValue(key);
		return PerMessageLogData.builder().uid(UUID.randomUUID().toString()).timestamp(getLastIntervalTimestamp())
				.total(false).key(key).value(value).build();
	}

	public PerMessageLogData getPointInTimeData(String key) {
		final Long value = getPointInTimeTotalValue(key);
		return PerMessageLogData.builder().uid(UUID.randomUUID().toString()).timestamp(getLastPointInTimeTimestamp())
				.total(true).key(key).value(value).build();
	}

}
