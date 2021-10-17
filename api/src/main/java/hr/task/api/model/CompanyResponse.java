package hr.task.api.model;

import hr.task.api.entity.CompanyLogData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

	private Long revenue;
	private Long cost;
	private Long profit;

	public static CompanyResponse mapFromLogData(CompanyLogData logData) {
		if (logData == null) {
			return null;
		}
		return new CompanyResponse(logData.getRevenue(), logData.getCost(), logData.getProfit());
	}

}
