package springboot.usecase.one.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiErrorRespnseFormatter {

	private String code;
	private String message;
	private Map<String, String> error;
	private HttpStatus status;

	public ApiErrorRespnseFormatter(HttpStatus status, Map<String, String> map) {
		super();
		this.status = status;
		this.error = map;
	}

	public ApiErrorRespnseFormatter(HttpStatus status, Throwable e) {
		super();
		this.status = status;
		this.message = e.getMessage();
	}

	public ApiErrorRespnseFormatter(HttpStatus status, CustomExceptionHandler e) {
		super();
		this.status = status;
		this.message = e.getMsg();
		this.code = e.getCode();
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

	public Map<String, String> getError() {
		return error;
	}

	public void setError(Map<String, String> error) {
		this.error = error;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
