package hr.task.channel.api.mockup.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MockupReq {

	@NotNull
	private String text;

	@NotBlank
	private String contact;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
