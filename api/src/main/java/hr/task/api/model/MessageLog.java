package hr.task.api.model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import hr.task.api.common.DateUtils;
import lombok.AccessLevel;
import lombok.Getter;

/**
 * Class for managing log state. Can be used to get last interval log state and
 * point in time total log state.
 * 
 * @author ljuric
 *
 */
public class MessageLog {

	private Map<String, Long> logTotal;
	private Map<String, Long> currentTotalState;
	private Map<String, Long> logInterval;
	private Map<String, Long> copyInterval;

	@Getter(AccessLevel.PROTECTED)
	private Long lastPointInTimeTimestamp;

	public MessageLog() {
		logInterval = new ConcurrentHashMap<>();
		logTotal = new ConcurrentHashMap<>();
	}

	/**
	 * Update total and interval log state.
	 * 
	 * @param key   log key
	 * @param delta value to add
	 */
	protected void update(String key, int delta) {
		logInterval.compute(key, (k, v) -> v == null ? delta : v + delta);
		logTotal.compute(key, (k, v) -> v == null ? delta : v + delta);
	}

	/**
	 * Call this method when point in time log state is wanted. Used both for
	 * interval and total log states.
	 */
	public void pointInTimeState() {
		currentTotalState = new ConcurrentHashMap<>(logTotal);
		copyInterval = new ConcurrentHashMap<>(logInterval);
		logInterval = new ConcurrentHashMap<>();
		lastPointInTimeTimestamp = DateUtils.currentTimestamp();
	}

	/**
	 * Get keys for the last interval log state. Must be called after
	 * {@link #pointInTimeState()} method.
	 * 
	 * @return log state keys
	 * @throws IllegalStateException if there is no last interval log state
	 */
	public Set<String> getIntervalKeys() {
		validateIntervalCopy();
		return copyInterval.keySet();
	}

	/**
	 * Get the value by key for the last interval log state. Must be called after
	 * {@link #pointInTimeState()} method. To get log state keys call
	 * {@link #getIntervalKeys()}
	 * 
	 * @param key log state key
	 * @return value for log state key
	 * @throws IllegalStateException if there is no last interval log state
	 */
	public Long getIntervalValue(String key) {
		validateIntervalCopy();
		return copyInterval.get(key);
	}

	private void validateIntervalCopy() {
		if (copyInterval == null) {
			throw new IllegalStateException("Call finishInterval method first!");
		}
	}

	/**
	 * Get keys for the last point in time log state. Must be called after
	 * {@link #pointInTimeTotalState()} method.
	 * 
	 * @return log state keys
	 * @throws IllegalStateException if there is no point in time log state
	 */
	public Set<String> getPointInTimeTotalKeys() {
		validateTotalCopy();
		return currentTotalState.keySet();
	}

	/**
	 * Get the value by key for the last point in time log state. Must be called
	 * after {@link #pointInTimeTotalState()} method. To get log state keys call
	 * {@link #getPointInTimeTotalKeys()}
	 * 
	 * @return log state keys
	 * @throws IllegalStateException if there is no point in time log state
	 */
	public Long getPointInTimeTotalValue(String key) {
		validateTotalCopy();
		return currentTotalState.get(key);
	}

	private void validateTotalCopy() {
		if (currentTotalState == null) {
			throw new IllegalStateException("Call currentTotalState method first!");
		}
	}

}
