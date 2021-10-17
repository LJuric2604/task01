package hr.task.api.repository;

import java.util.Optional;

import org.elasticsearch.search.aggregations.Aggregations;

import hr.task.api.entity.CompanyLogData;

public interface CompanyLogDataCustomRepository {

	Optional<CompanyLogData> findTotalByTimestamp(Long timestamp);

	Optional<Aggregations> findInInterval(Long startTimestamp, Long endTimestamp);

}
