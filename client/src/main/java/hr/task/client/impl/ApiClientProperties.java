package hr.task.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ApiClientProperties {

	@Value("${api.client.root.path}")
	private String rootPath;

	@Value("${api.client.messages.path}")
	private String messagesPath;

	@Value("${api.client.username}")
	private String username;

	@Value("${api.client.password}")
	private String password;

}
