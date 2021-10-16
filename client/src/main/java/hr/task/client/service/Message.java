package hr.task.client.service;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public final class Message {

	private final String client;
	private final String person;
	private final String text;
}
