package com.epam.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;

@ControllerAdvice
public class FeignExceptionHandler {

	@ExceptionHandler(value = FeignException.class)
	public ResponseEntity<ExceptionResponse> handleFeignException(FeignException ex, WebRequest req)
			throws JsonProcessingException {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimeStamp(new Date().toString());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
		exceptionResponse
				.setErrors(new ObjectMapper().readValue(ex.contentUTF8(), ExceptionResponse.class).getErrors());
		exceptionResponse.setPath(req.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookLimitExceededException.class)
	public ResponseEntity<ExceptionResponse> bookLimitExceedecExceptionHandler(BookLimitExceededException exception,
			WebRequest request) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimeStamp(new Date().toString());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
		exceptionResponse.setErrors(exception.getMessage());
		exceptionResponse.setPath(request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookNotMappedToUser.class)
	public ResponseEntity<ExceptionResponse> bookNotMappedToUserHandler(BookNotMappedToUser exception,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setTimeStamp(new Date().toString());
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.name());
		exceptionResponse.setErrors(exception.getMessage());
		exceptionResponse.setPath(request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
