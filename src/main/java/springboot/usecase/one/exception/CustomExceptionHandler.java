package springboot.usecase.one.exception;

public class CustomExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomExceptionHandler() {
		super();
	}



	public CustomExceptionHandler(String cause) {
		super(cause);
	}

	

}
