package hr.task.api.model;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

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

	private Map<String, AtomicLong> logTotal;
	private Map<String, AtomicLong> currentTotalState;
	private Map<String, AtomicLong> logInterval;
	private Map<String, AtomicLong> copyInterval;

	@Getter(AccessLevel.PROTECTED)
	private Long lastIntervalTimestamp;

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
		logInterval.computeIfAbsent(key, k -> new AtomicLong()).addAndGet(delta);
		logTotal.computeIfAbsent(key, k -> new AtomicLong()).addAndGet(delta);
	}

	/**
	 * Method to call when interval is finished. Call this method if you need
	 * current interval state. This method is naturally used with methods
	 * {@link #getIntervalKeys()} and {@link #getIntervalValue(String)}.
	 */
	public void finishInterval() {
		copyInterval = logInterval;
		logInterval = new ConcurrentHashMap<>();
		lastIntervalTimestamp = DateUtils.currentTimestamp();
	}

	/**
	 * Get keys for the last interval log state. Must be called after
	 * {@link #finishInterval()} method.
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
	 * {@link #finishInterval()} method. To get log state keys call
	 * {@link #getIntervalKeys()}
	 * 
	 * @param key log state key
	 * @return value for log state key
	 * @throws IllegalStateException if there is no last interval log state
	 */
	public Long getIntervalValue(String key) {
		validateIntervalCopy();
		AtomicLong value = copyInterval.get(key);
		return getImmutableValue(value);
	}

	private void validateIntervalCopy() {
		if (copyInterval == null) {
			throw new IllegalStateException("Call finishInterval method first!");
		}
	}

	/**
	 * Call this method when point in time total log state is wanted. This method is
	 * naturally used with methods {@link #getPointInTimeTotalKeys()} and
	 * {@link #getPointInTimeTotalValue(String)}.
	 */
	public void pointInTimeTotalState() {
		currentTotalState = new ConcurrentHashMap<>(logTotal);
		lastPointInTimeTimestamp = DateUtils.currentTimestamp();
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
		AtomicLong value = currentTotalState.get(key);
		return getImmutableValue(value);
	}

	private void validateTotalCopy() {
		if (currentTotalState == null) {
			throw new IllegalStateException("Call currentTotalState method first!");
		}
	}

	private Long getImmutableValue(AtomicLong value) {
		return value == null ? null : value.longValue();
	}

}
