package hr.task.channel.api.mockup.model;

public class SmsSender extends MockSender {

	public SmsSender(MockupReq request) {
		super(request);
	}

	@Override
	protected String constructMessage(MockupReq request) {
		StringBuilder builder = new StringBuilder();
		builder.append("SMS to ");
		builder.append(request.getContact());
		builder.append(": ");
		builder.append(request.getText());
		return builder.toString();
	}

}
