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
@Document(indexName = "per-message-logs")
public class PerMessageLogData {

	@Id
	private String uid;

	private String key;

	private Long value;

	private boolean total;

	private Long timestamp;

}
