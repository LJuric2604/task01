package hr.task.api.service.impl;

import java.time.LocalDateTime;

import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.springframework.stereotype.Service;

import hr.task.api.common.DateUtils;
import hr.task.api.model.PerMessageResponse;
import hr.task.api.repository.PerMessageLogDataRepository;
import hr.task.api.repository.impl.PerMessageLogDataCustomRepositoryImpl;
import hr.task.api.service.PerMessageService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerMessageServiceImpl implements PerMessageService {

	private final PerMessageLogDataRepository repository;

	@Override
	public PerMessageResponse getTotalData(String key, LocalDateTime timestamp) {
		final Long queryTimestamp = DateUtils.getTimestamp(timestamp);
		return repository.findTotalByTimestamp(key, queryTimestamp).map(PerMessageResponse::mapFromLogData)
				.orElseGet(PerMessageResponse::new);
	}

	@Override
	public PerMessageResponse getIntervalData(String key, LocalDateTime startInclusive, LocalDateTime endInclusive) {
		return repository
				.findInInterval(key, DateUtils.getTimestamp(startInclusive), DateUtils.getTimestamp(endInclusive))
				.map(aggregations -> this.mapAggregations(aggregations, key)).orElseGet(PerMessageResponse::new);
	}

	private PerMessageResponse mapAggregations(Aggregations aggregations, String key) {
		final Sum counter = aggregations.get(PerMessageLogDataCustomRepositoryImpl.AGG_COUNTER);

		return new PerMessageResponse(key, (long) counter.getValue());
	}

}
