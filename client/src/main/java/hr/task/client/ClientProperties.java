package hr.task.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class ClientProperties {

	@Value("${jobs.per.task}")
	private int jobsPerTask;

}
