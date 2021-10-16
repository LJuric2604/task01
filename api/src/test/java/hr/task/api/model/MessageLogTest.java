package hr.task.api.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MessageLogTest {

	@Test
	public void givenMessageLogNotFinishedIntervalWhenGetInternalKeysExpectIllegalStateException() {
		final var log = new MessageLog();
		log.update("key", 1);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(log::getIntervalKeys);
	}

	@Test
	public void givenMessageLogNotFinishedIntervalWhenGetInternalValueExpectIllegalStateException() {
		final var log = new MessageLog();
		log.update("key", 1);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> log.getIntervalValue("key"));
	}

	@Test
	public void givenMessageLogNotPointInTimeWhenGetPointInTimeKeysExpectIllegalStateException() {
		final var log = new MessageLog();
		log.update("key", 1);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(log::getPointInTimeTotalKeys);
	}

	@Test
	public void givenMessageLogNotPointInTimeWhenGetPointInTimeValueExpectIllegalStateException() {
		final var log = new MessageLog();
		log.update("key", 1);
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> log.getPointInTimeTotalValue("key"));
	}

	@Test
	public void givenMessageLogWithoutKeyWhenGetInternalValueExpectNull() {
		final var log = new MessageLog();
		log.update("key", 1);
		log.finishInterval();
		Long value = log.getIntervalValue("not_key");
		assertThat(value).isNull();
	}

	@Test
	public void givenMessageLogWithoutKeyWhenGetPointInTimeValueExpectNull() {
		final var log = new MessageLog();
		log.update("key", 1);
		log.pointInTimeTotalState();
		Long value = log.getPointInTimeTotalValue("not_key");
		assertThat(value).isNull();
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, -2, 50, 100, Integer.MAX_VALUE })
	public void givenMessageLogWhenIntervalFinishedExpectValue(int value) {
		final var log = new MessageLog();
		log.update("key", value);
		log.finishInterval();
		Long expectedValue = log.getIntervalValue("key");
		assertThat(expectedValue).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, -2, 50, 100, Integer.MAX_VALUE })
	public void givenMessageLogWhenPointInTimeExpectValue(int value) {
		final var log = new MessageLog();
		log.update("key", value);
		log.pointInTimeTotalState();
		Long expectedValue = log.getPointInTimeTotalValue("key");
		assertThat(expectedValue).isEqualTo(value);
	}

	@ParameterizedTest
	@MethodSource("generateKeys")
	public void givenMessageLogWithKeysWhenIntervaFinishedExpectAllKeys(String[] keys) {
		final var log = new MessageLog();
		for (String key : keys) {
			log.update(key, 1);
		}
		log.finishInterval();
		Set<String> expectedKeys = log.getIntervalKeys();
		assertThat(expectedKeys).containsExactly(keys);
	}

	@ParameterizedTest
	@MethodSource("generateKeys")
	public void givenMessageLogWithKeysWhenPointInTimeExpectAllKeys(String[] keys) {
		final var log = new MessageLog();
		for (String key : keys) {
			log.update(key, 1);
		}
		log.pointInTimeTotalState();
		Set<String> expectedKeys = log.getPointInTimeTotalKeys();
		assertThat(expectedKeys).containsExactly(keys);
	}

	@ParameterizedTest
	@MethodSource("generateValues")
	public void givenMessageLogWhenIntervalFinishedExpectSum(int[] values) {
		final var log = new MessageLog();
		long sum = 0;
		for (int value : values) {
			log.update("key", value);
			sum += value;
		}
		log.finishInterval();
		Long expectedValue = log.getIntervalValue("key");
		assertThat(expectedValue).isEqualTo(sum);
	}

	@ParameterizedTest
	@MethodSource("generateValues")
	public void givenMessageLogWhenPointInTimeExpectSum(int[] values) {
		final var log = new MessageLog();
		long sum = 0;
		for (int value : values) {
			log.update("key", value);
			sum += value;
		}
		log.pointInTimeTotalState();
		Long expectedValue = log.getPointInTimeTotalValue("key");
		assertThat(expectedValue).isEqualTo(sum);
	}

	@ParameterizedTest
	@MethodSource("generateTwoTimeValues")
	public void givenMessageLogWhenTwoTimesLogIntervalExpectSecondIntervalSum(int[] valuesFirst, int[] valuesSecond) {
		final var log = new MessageLog();
		for (int value : valuesFirst) {
			log.update("key", value);
		}
		log.finishInterval();
		long sum = 0;
		for (int value : valuesSecond) {
			log.update("key", value);
			sum += value;
		}
		log.finishInterval();
		Long expectedValue = log.getIntervalValue("key");
		assertThat(expectedValue).isEqualTo(sum);
	}

	@ParameterizedTest
	@MethodSource("generateTwoTimeValues")
	public void givenMessageLogWhenTwoTimesPointInTimeExpectTotalSum(int[] valuesFirst, int[] valuesSecond) {
		final var log = new MessageLog();
		long sum = 0;
		for (int value : valuesFirst) {
			log.update("key", value);
			sum += value;
		}
		log.pointInTimeTotalState();
		for (int value : valuesSecond) {
			log.update("key", value);
			sum += value;
		}
		log.pointInTimeTotalState();
		Long expectedValue = log.getPointInTimeTotalValue("key");
		assertThat(expectedValue).isEqualTo(sum);
	}

	@ParameterizedTest
	@MethodSource("generateTwoTimeValues")
	public void givenMessageLogWhenAsyncUpdateExpectTotalSumOnIntervalFinished(int[] valuesFirst, int[] valuesSecond)
			throws InterruptedException {
		final var log = new MessageLog();
		final var sum = new AtomicLong();

		final var anotherTask = new Thread() {
			@Override
			public void run() {
				for (int value : valuesFirst) {
					log.update("key", value);
					sum.addAndGet(value);
				}
			};
		};

		anotherTask.start();

		for (int value : valuesSecond) {
			log.update("key", value);
			sum.addAndGet(value);
		}

		anotherTask.join();

		log.finishInterval();
		Long expectedValue = log.getIntervalValue("key");
		assertThat(expectedValue).isEqualTo(sum.longValue());
	}

	@ParameterizedTest
	@MethodSource("generateTwoTimeValues")
	public void givenMessageLogWhenAsyncUpdateExpectTotalSumOnPointInTime(int[] valuesFirst, int[] valuesSecond)
			throws InterruptedException {
		final var log = new MessageLog();
		final var sum = new AtomicLong();

		final var anotherTask = new Thread() {
			@Override
			public void run() {
				for (int value : valuesFirst) {
					log.update("key", value);
					sum.addAndGet(value);
				}
			};
		};

		anotherTask.start();

		for (int value : valuesSecond) {
			log.update("key", value);
			sum.addAndGet(value);
		}

		anotherTask.join();

		log.pointInTimeTotalState();
		Long expectedValue = log.getPointInTimeTotalValue("key");
		assertThat(expectedValue).isEqualTo(sum.longValue());
	}

	private static Stream<Arguments> generateKeys() {
		return Stream.of(Arguments.of((Object) new String[] { "key1" }),
				Arguments.of((Object) new String[] { "key1", "key2", "key3" }));
	}

	private static Stream<Arguments> generateValues() {
		return Stream.of(Arguments.of(new int[] { 1 }), Arguments.of(new int[] { 1, 2, 100, 105, 0, 1000 }),
				Arguments.of(new int[] { -1, -213, 213, 123 }), Arguments.of(new int[] { 1, Integer.MAX_VALUE, 2 }));
	}

	private static Stream<Arguments> generateTwoTimeValues() {
		return Stream.of(Arguments.of(new int[] { 1 }, new int[] { 0 }),
				Arguments.of(new int[] { 1, 2, 100, 105, 0, 1000 }, new int[] { 50, 100, 1, 213 }),
				Arguments.of(new int[] { -1, -213, 213, 123 }, new int[] { 1 }),
				Arguments.of(new int[] { 1, Integer.MAX_VALUE, 2 }, new int[] { 1, Integer.MAX_VALUE, 2 }));
	}

}
