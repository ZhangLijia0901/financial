package boot.app;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(RuntimeException.class)
	public String runtimeException(RuntimeException e) {
		System.err.println(e.getMessage());
		return e.getMessage();
	}

	@ExceptionHandler(Exception.class)
	public String exception(Exception e) {
		System.err.println(e.getMessage());
		return e.getMessage();
	}
}
