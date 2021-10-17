package hr.task.api.repository.impl;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

public class LogDataCustomRepositoryImpl {

	protected static final String KEY_FIELD = "key";
	protected static final String TIMESTAMP_FIELD = "timestamp";
	protected static final String TOTAL_FIELD = "total";

	protected QueryBuilder buildLteTimestampFilter(Long timestamp) {
		return new RangeQueryBuilder(TIMESTAMP_FIELD).lte(timestamp);
	}

	protected QueryBuilder buildGteTimestampFilter(Long timestamp) {
		return new RangeQueryBuilder(TIMESTAMP_FIELD).gte(timestamp);
	}

	protected FieldSortBuilder getTimestampSort() {
		return SortBuilders.fieldSort(TIMESTAMP_FIELD).order(SortOrder.DESC);
	}

	protected QueryBuilder buildTotalFilter(boolean isTotal) {
		return new TermQueryBuilder(TOTAL_FIELD, isTotal);
	}

	protected QueryBuilder buildKeyFilter(String key) {
		return new MatchQueryBuilder(KEY_FIELD, key);
	}

}
