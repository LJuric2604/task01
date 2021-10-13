package hr.task.api.model;

import hr.task.api.entity.Client;
import hr.task.api.entity.Person;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Message {
	
	private Client client;
	private Person person;
	private String text;

}
