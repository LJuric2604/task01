package hr.task.api.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Message request DTO object.
 * 
 * @author ljuric
 *
 */
@Getter
@Setter
public class MessageReq {

	private String client;
	private String person;
	private String text;

}
