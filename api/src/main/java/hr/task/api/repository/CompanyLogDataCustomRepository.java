package hr.task.api.repository;

import java.util.Optional;

import org.elasticsearch.search.aggregations.Aggregations;

import hr.task.api.entity.CompanyLogData;

public interface CompanyLogDataCustomRepository {

	/**
	 * Find total company log data for specified timestamp. Total data must be
	 * correct log data at that moment.
	 * 
	 * @param timestamp moment of observation
	 * @return
	 */
	Optional<CompanyLogData> findTotalByTimestamp(Long timestamp);

	/**
	 * Find aggregated company log data for specific interval in time.
	 * 
	 * @param startTimestamp
	 * @param endTimestamp
	 * @return
	 */
	Optional<Aggregations> findInInterval(Long startTimestamp, Long endTimestamp);

}
