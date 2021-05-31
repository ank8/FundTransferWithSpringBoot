package springboot.usecase.one.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

public class ApiErrorRespnseFormatter {
	private HttpStatus status;
	private String message;
	private Map<String, String> map;

	public ApiErrorRespnseFormatter(HttpStatus status, Map<String, String> map) {
		super();
		this.status = status;
		this.map = map;
	}

	public ApiErrorRespnseFormatter(HttpStatus status, Throwable e) {
		super();
		this.status = status;
		this.message = e.getMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
