package hr.task.api.service;

import java.time.LocalDateTime;

import hr.task.api.model.PerMessageResponse;

public interface PerMessageService {

	PerMessageResponse getTotalData(String key, LocalDateTime timestamp);

	PerMessageResponse getIntervalData(String key, LocalDateTime startInclusive, LocalDateTime endInclusive);

}
