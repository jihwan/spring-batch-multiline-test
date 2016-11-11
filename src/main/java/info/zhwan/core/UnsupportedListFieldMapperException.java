package info.zhwan.core;

public class UnsupportedListFieldMapperException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedListFieldMapperException() {
	}

	public UnsupportedListFieldMapperException(String message) {
		super(message);
	}

	public UnsupportedListFieldMapperException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedListFieldMapperException(Throwable cause) {
		super(cause);
	}
}
