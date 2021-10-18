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
import hr.task.api.entity.PerMessageLogData;
import hr.task.api.model.PerMessageResponse;
import hr.task.api.repository.PerMessageLogDataRepository;
import hr.task.api.repository.impl.LogDataRepositoryImpl;
import hr.task.api.service.PerMessageService;

@ExtendWith(MockitoExtension.class)
public class PerMessageServiceImplTest {

	@Mock
	private PerMessageLogDataRepository repository;

	private PerMessageService perMessageService;

	private static LocalDateTime datetime;
	private static Long timestamp;

	@BeforeAll
	static void setup() {
		datetime = LocalDateTime.now();
		timestamp = DateUtils.getTimestamp(datetime);
	}

	@BeforeEach
	void setupService() {
		perMessageService = new PerMessageServiceImpl(repository);
	}

	@Test
	public void whenGetTotalDataGivenAllValueExpectThoseValueInResponse() {
		Optional<PerMessageLogData> returnedValue = Optional.of(new PerMessageLogData("uid", "P1", 23L, true, null));
		when(repository.findTotalByTimestamp("P1", timestamp)).thenReturn(returnedValue);

		PerMessageResponse expectedValue = new PerMessageResponse("P1", 23L);
		PerMessageResponse response = perMessageService.getTotalData("P1", datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetTotalDataGivenEmptyOptionalValueExpectEmptyResponse() {
		Optional<PerMessageLogData> returnedValue = Optional.ofNullable(null);
		when(repository.findTotalByTimestamp("P1", timestamp)).thenReturn(returnedValue);

		PerMessageResponse expectedValue = new PerMessageResponse();
		PerMessageResponse response = perMessageService.getTotalData("P1", datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetIntervalDataGivenAllValueExpectThoseValueInResponse() {
		Sum counter = new InternalSum(LogDataRepositoryImpl.AGG_COUNTER, 23, null, null);

		List<Aggregation> aggregations = Arrays.asList(counter);
		Optional<Aggregations> returnedValue = Optional.of(new Aggregations(aggregations));
		when(repository.findInInterval("P1", timestamp, timestamp)).thenReturn(returnedValue);

		PerMessageResponse expectedValue = new PerMessageResponse("P1", 23L);
		PerMessageResponse response = perMessageService.getIntervalData("P1", datetime, datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

	@Test
	public void whenGetIntervalDataGivenEmptyOptionalValueExpectEmptyResponse() {
		Optional<Aggregations> returnedValue = Optional.ofNullable(null);
		when(repository.findInInterval("P1", timestamp, timestamp)).thenReturn(returnedValue);

		PerMessageResponse expectedValue = new PerMessageResponse();
		PerMessageResponse response = perMessageService.getIntervalData("P1", datetime, datetime);

		assertThat(response).isEqualTo(expectedValue);
	}

}
