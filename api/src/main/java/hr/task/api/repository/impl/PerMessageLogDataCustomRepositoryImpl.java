package hr.task.api.repository.impl;

import java.util.Optional;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import hr.task.api.entity.PerMessageLogData;
import hr.task.api.repository.PerMessageLogDataCustomRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerMessageLogDataCustomRepositoryImpl extends LogDataRepositoryImpl
		implements PerMessageLogDataCustomRepository {

	private final ElasticsearchOperations operations;

	@Override
	public Optional<PerMessageLogData> findTotalByTimestamp(String key, Long timestamp) {
		NativeSearchQuery query = new NativeSearchQueryBuilder().withSort(getTimestampSort())
				.withQuery(new BoolQueryBuilder().filter(buildTotalFilter(true))
						.filter(buildLteTimestampFilter(timestamp)).filter(buildKeyFilter(key)))
				.build();

		SearchHit<PerMessageLogData> response = operations.searchOne(query, PerMessageLogData.class);
		return Optional.ofNullable(response).map(SearchHit::getContent);
	}

	@Override
	public Optional<Aggregations> findInInterval(String key, Long startTimestamp, Long endTimestamp) {
		NativeSearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(new BoolQueryBuilder().filter(buildTotalFilter(false)).filter(buildKeyFilter(key))
						.filter(buildGteTimestampFilter(startTimestamp)).filter(buildLteTimestampFilter(endTimestamp)))
				.addAggregation(buildSumAggregation(AGG_COUNTER, FIELD_VALUE)).build();

		SearchHits<PerMessageLogData> response = operations.search(query, PerMessageLogData.class);
		return Optional.ofNullable(response).map(SearchHits::getAggregations);
	}

}
