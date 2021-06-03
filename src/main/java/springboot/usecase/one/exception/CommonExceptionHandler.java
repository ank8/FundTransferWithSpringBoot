package springboot.usecase.one.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> map = new HashMap<>();
		ex.getBindingResult().getAllErrors()
				.forEach(err -> map.put(((FieldError) err).getField(), err.getDefaultMessage()));
		return buildResponse(new ApiErrorRespnseFormatter(HttpStatus.BAD_REQUEST, map));
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return buildResponse(new ApiErrorRespnseFormatter(HttpStatus.BAD_REQUEST, ex));
	}

	@ExceptionHandler(CustomExceptionHandler.class)
	public ResponseEntity<Object> handleCommonExceptionHandler(CustomExceptionHandler ex, WebRequest request) {
		return buildResponse(new ApiErrorRespnseFormatter(HttpStatus.BAD_REQUEST, ex));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> handleCommonExceptionHandler(RuntimeException ex, WebRequest request) {
		return buildResponse(new ApiErrorRespnseFormatter(HttpStatus.BAD_REQUEST, ex));
	}

	private ResponseEntity<Object> buildResponse(ApiErrorRespnseFormatter err) {
		return new ResponseEntity<>(err, err.getStatus());
	}
}
