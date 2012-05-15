package org.example.demo.auth;

public class NotAuthenticatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAuthenticatedException() {
		super();
	}

	public NotAuthenticatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthenticatedException(String message) {
		super(message);
	}

	public NotAuthenticatedException(Throwable cause) {
		super(cause);
	}

}
