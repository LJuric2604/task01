package hr.task.api.repository;

import java.util.Optional;

import org.elasticsearch.search.aggregations.Aggregations;

import hr.task.api.entity.PerMessageLogData;

public interface PerMessageLogDataCustomRepository {

	Optional<PerMessageLogData> findTotalByTimestamp(String key, Long timestamp);

	Optional<Aggregations> findInInterval(String key, Long startTimestamp, Long endTimestamp);

}
