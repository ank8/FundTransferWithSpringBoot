package springboot.usecase.one.exception;

public class CustomExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String code;
	private final String msg;

	public CustomExceptionHandler(String code, String cause) {
		super();
		this.code = code;
		this.msg = cause;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
