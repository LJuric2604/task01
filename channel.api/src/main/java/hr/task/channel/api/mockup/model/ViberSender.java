package hr.task.channel.api.mockup.model;

public class ViberSender extends MockSender {

	public ViberSender(MockupReq request) {
		super(request);
	}

	@Override
	protected String constructMessage(MockupReq request) {
		StringBuilder builder = new StringBuilder();
		builder.append("Viber (to ");
		builder.append(request.getContact());
		builder.append(" ): ");
		builder.append(request.getText());
		return builder.toString();
	}

}
