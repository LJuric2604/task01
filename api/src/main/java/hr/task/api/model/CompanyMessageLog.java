package hr.task.api.model;

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

}
