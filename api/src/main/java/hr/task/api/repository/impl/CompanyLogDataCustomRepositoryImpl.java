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

import hr.task.api.entity.CompanyLogData;
import hr.task.api.repository.CompanyLogDataCustomRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyLogDataCustomRepositoryImpl extends LogDataRepositoryImpl
		implements CompanyLogDataCustomRepository {

	private final ElasticsearchOperations operations;

	@Override
	public Optional<CompanyLogData> findTotalByTimestamp(Long timestamp) {
		NativeSearchQuery query = new NativeSearchQueryBuilder().withSort(getTimestampSort()).withQuery(
				new BoolQueryBuilder().filter(buildTotalFilter(true)).filter(buildLteTimestampFilter(timestamp)))
				.build();

		SearchHit<CompanyLogData> response = operations.searchOne(query, CompanyLogData.class);
		return Optional.ofNullable(response).map(SearchHit::getContent);
	}

	@Override
	public Optional<Aggregations> findInInterval(Long startTimestamp, Long endTimestamp) {
		NativeSearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(new BoolQueryBuilder().filter(buildTotalFilter(false))
						.filter(buildGteTimestampFilter(startTimestamp)).filter(buildLteTimestampFilter(endTimestamp)))
				.addAggregation(buildSumAggregation(AGG_REVENUE, FIELD_REVENUE))
				.addAggregation(buildSumAggregation(AGG_COST, FIELD_COST))
				.addAggregation(buildSumAggregation(AGG_PROFIT, FIELD_PROFIT)).build();

		SearchHits<CompanyLogData> response = operations.search(query, CompanyLogData.class);
		return Optional.ofNullable(response).map(SearchHits::getAggregations);
	}

}
