package hr.task.api.model;

public class CounterMessageLog extends MessageLog {

	public void increment(String key) {
		update(key, 1);
	}

}
