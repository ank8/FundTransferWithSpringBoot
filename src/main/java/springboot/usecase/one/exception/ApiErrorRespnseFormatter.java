package springboot.usecase.one.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import springboot.usecase.one.constants.CommonConstants;

@JsonInclude(Include.NON_NULL)
public class ApiErrorRespnseFormatter {

	private Map<String, Object> response;

	public ApiErrorRespnseFormatter(HttpStatus status, Map<String, Object> map) {
		super();
		this.response = map;
		this.response.put(CommonConstants.CODE, status.value() + "");
		this.response.put(CommonConstants.MESSAGE, status.getReasonPhrase());
	}

	public ApiErrorRespnseFormatter(HttpStatus status, Throwable e) {
		super();
		this.response = new HashMap<>();
		this.response.put(CommonConstants.CODE, status.value() + "");
		this.response.put(CommonConstants.MESSAGE, e.getLocalizedMessage());
	}

	public ApiErrorRespnseFormatter(CustomExceptionHandler e) {
		super();
		this.response = new HashMap<>();
		this.response.put(CommonConstants.CODE, e.getCode());
		this.response.put(CommonConstants.MESSAGE, e.getMsg());
	}

	public Map<String, Object> getResponse() {
		return response;
	}

	public void setResponse(Map<String, Object> response) {
		this.response = response;
	}

}
