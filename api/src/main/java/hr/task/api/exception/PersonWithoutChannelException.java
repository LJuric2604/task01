package hr.task.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PersonWithoutChannelException extends ResponseStatusException {

	private static final long serialVersionUID = 2629513213549118740L;

	public PersonWithoutChannelException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "Person does not have any channel!");
	}
}
