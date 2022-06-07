package com.epam.clients;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.epam.dto.BookDto;

import feign.FeignException;

public class BookClientImpl implements BookClient {

	public final Throwable cause;

	public BookClientImpl(Throwable cause) {

		this.cause = cause;
	}

	@Override
	public ResponseEntity<List<BookDto>> getBooks() {
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<BookDto> getBook(int bookId) {
		
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		} else {
			return new ResponseEntity<>(new BookDto(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public ResponseEntity<BookDto> addBook(BookDto bookDto) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		}
		return new ResponseEntity<>(new BookDto(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<BookDto> updateBook(int bookId, BookDto bookDto) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		}
		return new ResponseEntity<>(new BookDto(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<String> deleteBook(int bookId) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		}
		return new ResponseEntity<>("", HttpStatus.SERVICE_UNAVAILABLE);
	}

}
