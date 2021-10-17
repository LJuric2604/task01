package hr.task.api.service;

import java.time.LocalDateTime;

import hr.task.api.model.CompanyResponse;

public interface CompanyService {

	CompanyResponse getTotalData(LocalDateTime timestamp);

	CompanyResponse getIntervalData(LocalDateTime startInclusive, LocalDateTime endInclusive);

}
