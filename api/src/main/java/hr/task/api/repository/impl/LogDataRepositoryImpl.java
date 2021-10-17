package hr.task.api.repository.impl;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

/**
 * Basic class with common methods for log custom repository implementations.
 * 
 * @author ljuric
 *
 */
public class LogDataRepositoryImpl {

	protected static final String FIELD_KEY = "key";
	protected static final String FIELD_TIMESTAMP = "timestamp";
	protected static final String FIELD_TOTAL = "total";
	protected final static String FIELD_COST = "cost";
	protected final static String FIELD_REVENUE = "revenue";
	protected final static String FIELD_PROFIT = "profit";
	protected final static String FIELD_VALUE = "value";

	public final static String AGG_COST = "COST_SUM";
	public final static String AGG_REVENUE = "REVENUE_SUM";
	public final static String AGG_PROFIT = "PROFIT_SUM";
	public final static String AGG_COUNTER = "COUNTER";

	protected QueryBuilder buildLteTimestampFilter(Long timestamp) {
		return new RangeQueryBuilder(FIELD_TIMESTAMP).lte(timestamp);
	}

	protected QueryBuilder buildGteTimestampFilter(Long timestamp) {
		return new RangeQueryBuilder(FIELD_TIMESTAMP).gte(timestamp);
	}

	protected FieldSortBuilder getTimestampSort() {
		return SortBuilders.fieldSort(FIELD_TIMESTAMP).order(SortOrder.DESC);
	}

	protected QueryBuilder buildTotalFilter(boolean isTotal) {
		return new TermQueryBuilder(FIELD_TOTAL, isTotal);
	}

	protected QueryBuilder buildKeyFilter(String key) {
		return new MatchQueryBuilder(FIELD_KEY, key);
	}

	protected AbstractAggregationBuilder<?> buildSumAggregation(String aggregationName, String fieldName) {
		return AggregationBuilders.sum(aggregationName).field(fieldName);
	}

}
