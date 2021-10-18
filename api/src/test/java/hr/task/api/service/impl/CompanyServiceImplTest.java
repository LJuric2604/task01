package hr.task.api.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.metrics.InternalSum;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hr.task.api.common.DateUtils;
import hr.task.api.entity.CompanyLogData;
import hr.task.api.model.CompanyResponse;
import hr.task.api.repository.CompanyLogDataRepository;
import hr.task.api.repository.impl.LogDataRepositoryImpl;
import hr.task.api.service.CompanyService;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceImplTest {

	@Mock
	private CompanyLogDataRepository repository;

	private CompanyService companyService;

	private static LocalDateTime datetime;
	private static Long timestamp;

	@BeforeAll
	static void setup() {
		datetime = LocalDateTime.now();
		timestamp = DateUtils.getTimestamp(datetime);
	}

	@BeforeEach
	void setupService() {
		companyService = new CompanyServiceImpl(repository);
	}

	@Test
	public void whenGetTotalDataGivenAllValueExpectThoseValueInResponse() {
		Optional<CompanyLogData> returnedValue = Optional.of(new CompanyLogData("uid", 150L, 50L, 100L, true, null));
		when(repository.findTotalByTimestamp(timestamp)).thenReturn(returnedValue);

		CompanyResponse expectedValue = new CompanyResponse(150L, 50L, 100L);
		CompanyResponse response = companyService.getTotalData(datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetTotalDataGivenEmptyOptionalValueExpectEmptyResponse() {
		Optional<CompanyLogData> returnedValue = Optional.ofNullable(null);
		when(repository.findTotalByTimestamp(timestamp)).thenReturn(returnedValue);

		CompanyResponse expectedValue = new CompanyResponse();
		CompanyResponse response = companyService.getTotalData(datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetIntervalDataGivenAllValueExpectThoseValueInResponse() {
		Sum revenue = new InternalSum(LogDataRepositoryImpl.AGG_REVENUE, 150, null, null);
		Sum cost = new InternalSum(LogDataRepositoryImpl.AGG_COST, 50, null, null);
		Sum profit = new InternalSum(LogDataRepositoryImpl.AGG_PROFIT, 100, null, null);

		List<Aggregation> aggregations = Arrays.asList(revenue, cost, profit);
		Optional<Aggregations> returnedValue = Optional.of(new Aggregations(aggregations));
		when(repository.findInInterval(timestamp, timestamp)).thenReturn(returnedValue);

		CompanyResponse expectedValue = new CompanyResponse(150L, 50L, 100L);
		CompanyResponse response = companyService.getIntervalData(datetime, datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetIntervalDataGivenEmptyOptionalValueExpectEmptyResponse() {
		Optional<Aggregations> returnedValue = Optional.ofNullable(null);
		when(repository.findInInterval(timestamp, timestamp)).thenReturn(returnedValue);

		CompanyResponse expectedValue = new CompanyResponse();
		CompanyResponse response = companyService.getIntervalData(datetime, datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

}
