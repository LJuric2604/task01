package hr.task.api.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

	public static Long getTimestamp(LocalDateTime timestamp) {
		return timestamp.toEpochSecond(ZoneOffset.UTC);
	}

	public static Long currentTimestamp() {
		return getTimestamp(LocalDateTime.now());
	}

}
