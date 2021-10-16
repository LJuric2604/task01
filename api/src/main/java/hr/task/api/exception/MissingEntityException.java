package hr.task.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception that occurs when entity is not found in database.
 * 
 * @author ljuric
 *
 */
public class MissingEntityException extends ResponseStatusException {

	private static final long serialVersionUID = -6852715623088354332L;

	public MissingEntityException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}

}
