package hr.task.api.model;

import java.util.UUID;

import hr.task.api.entity.CompanyLogData;

public class CompanyMessageLog extends MessageLog {

	private static final String REVENUE = "Revenue";
	private static final String COST = "Cost";
	private static final String PROFIT = "Profit";

	public void updateRevenue(int revenue) {
		super.update(REVENUE, revenue);
	}

	public void updateCost(int cost) {
		super.update(COST, cost);
	}

	public void updateProfit(int revenue, int cost) {
		super.update(PROFIT, revenue - cost);
	}

	public CompanyLogData getIntervalData() {
		final Long cost = getIntervalValue(COST);
		final Long revenue = getIntervalValue(REVENUE);
		final Long profit = getIntervalValue(PROFIT);
		if (cost == null && revenue == null && profit == null) {
			return null;
		}
		return CompanyLogData.builder().uid(UUID.randomUUID().toString()).timestamp(getLastIntervalTimestamp())
				.total(false).cost(cost).revenue(revenue).profit(profit).build();
	}

	public CompanyLogData getPointInTimeData() {
		final Long cost = getPointInTimeTotalValue(COST);
		final Long revenue = getPointInTimeTotalValue(REVENUE);
		final Long profit = getPointInTimeTotalValue(PROFIT);
		if (cost == null && revenue == null && profit == null) {
			return null;
		}
		return CompanyLogData.builder().uid(UUID.randomUUID().toString()).timestamp(getLastPointInTimeTimestamp())
				.total(true).cost(cost).revenue(revenue).profit(profit).build();
	}

}
