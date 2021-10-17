package hr.task.api.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "company-logs")
public class CompanyLogData {

	@Id
	private String uid;

	private Long revenue;

	private Long cost;

	private Long profit;

	private boolean total;

	private Long timestamp;

}
