package hr.task.api.model;

import hr.task.api.entity.PerMessageLogData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerMessageResponse {

	private String key;
	private Long count;

	public static PerMessageResponse mapFromLogData(PerMessageLogData logData) {
		if (logData == null) {
			return null;
		}
		return new PerMessageResponse(logData.getKey(), logData.getValue());
	}

}
