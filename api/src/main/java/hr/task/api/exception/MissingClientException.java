package hr.task.api.exception;

public class MissingClientException extends MissingEntityException {

	private static final long serialVersionUID = -6852715623088354332L;

	public MissingClientException() {
		super("There is no such client!");
	}

}
