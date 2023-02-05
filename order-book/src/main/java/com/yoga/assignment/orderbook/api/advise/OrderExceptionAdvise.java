package com.yoga.assignment.orderbook.api.advise;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yoga.assignment.orderbook.api.exception.OrderException;

@RestControllerAdvice
public class OrderExceptionAdvise {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleOrderRequestDtoExceptions(MethodArgumentNotValidException exception) {
		Map<String, String> errorMap = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(error -> {
			errorMap.put(error.getField(), error.getDefaultMessage());
		});
		return errorMap;
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(OrderException.class)
	public Map<String, String> handleOrderRequestDtoExceptions(OrderException exception) {
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("ErrorMessage", exception.getMessage());
		return errorMap;
	}
}
