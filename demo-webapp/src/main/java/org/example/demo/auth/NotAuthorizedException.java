package org.example.demo.auth;

/**
 * Resource required a role that logged user doesn't have.
 * 
 * @author dometec
 *
 */
public class NotAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotAuthorizedException() {
		super();
	}

	public NotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthorizedException(String message) {
		super(message);
	}

	public NotAuthorizedException(Throwable cause) {
		super(cause);
	}

}
