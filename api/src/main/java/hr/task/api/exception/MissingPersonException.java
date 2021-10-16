package hr.task.api.exception;

public class MissingPersonException extends MissingEntityException {

	private static final long serialVersionUID = -6852715623088354332L;

	public MissingPersonException() {
		super("There is no such person!");
	}

}
