package hr.task.channel.api.mockup.model;

public class WhatsAppSender extends MockSender {

	public WhatsAppSender(MockupReq request) {
		super(request);
	}

	@Override
	protected String constructMessage(MockupReq request) {
		StringBuilder builder = new StringBuilder();
		builder.append("\"");
		builder.append(request.getText());
		builder.append("\" to ");
		builder.append(request.getContact());
		builder.append(" (WhatsApp)");
		return builder.toString();
	}

}
