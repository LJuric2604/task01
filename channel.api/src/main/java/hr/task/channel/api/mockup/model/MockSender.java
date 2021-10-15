package hr.task.channel.api.mockup.model;

public abstract class MockSender {

	private final String message;

	public MockSender(MockupReq request) {
		this.message = constructMessage(request);
	}

	protected abstract String constructMessage(MockupReq request);

	public final void send() {
		System.out.println(message);
	}

}
