package hr.task.api.service.impl;

import java.time.LocalDateTime;

import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.springframework.stereotype.Service;

import hr.task.api.common.DateUtils;
import hr.task.api.model.CompanyResponse;
import hr.task.api.repository.CompanyLogDataRepository;
import hr.task.api.repository.impl.LogDataRepositoryImpl;
import hr.task.api.service.CompanyService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyLogDataRepository repository;

	@Override
	public CompanyResponse getTotalData(LocalDateTime timestamp) {
		final Long queryTimestamp = DateUtils.getTimestamp(timestamp);
		return repository.findTotalByTimestamp(queryTimestamp).map(CompanyResponse::mapFromLogData)
				.orElseGet(CompanyResponse::new);
	}

	@Override
	public CompanyResponse getIntervalData(LocalDateTime startInclusive, LocalDateTime endInclusive) {
		return repository.findInInterval(DateUtils.getTimestamp(startInclusive), DateUtils.getTimestamp(endInclusive))
				.map(this::mapAggregations).orElseGet(CompanyResponse::new);
	}

	private CompanyResponse mapAggregations(Aggregations aggregations) {
		final Sum revenue = aggregations.get(LogDataRepositoryImpl.AGG_REVENUE);
		final Sum cost = aggregations.get(LogDataRepositoryImpl.AGG_COST);
		final Sum profit = aggregations.get(LogDataRepositoryImpl.AGG_PROFIT);

		return new CompanyResponse((long) revenue.getValue(), (long) cost.getValue(), (long) profit.getValue());
	}

}
