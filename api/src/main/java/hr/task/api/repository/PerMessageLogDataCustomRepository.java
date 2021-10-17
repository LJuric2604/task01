package hr.task.api.repository;

import java.util.Optional;

import org.elasticsearch.search.aggregations.Aggregations;

import hr.task.api.entity.PerMessageLogData;

public interface PerMessageLogDataCustomRepository {

	/**
	 * Find total per message log data for specified timestamp and key. Total data
	 * must be correct log data at that moment.
	 * 
	 * @param key
	 * @param timestamp moment of observation
	 * @return
	 */
	Optional<PerMessageLogData> findTotalByTimestamp(String key, Long timestamp);

	/**
	 * Find aggregated per message log data for specific interval in time for given
	 * per message key.
	 * 
	 * @param key
	 * @param startTimestamp
	 * @param endTimestamp
	 * @return
	 */
	Optional<Aggregations> findInInterval(String key, Long startTimestamp, Long endTimestamp);

}
