package com.epam.clients;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.epam.dto.BookDto;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "library-books", fallbackFactory = BookFallBackFactory.class)
@LoadBalancerClient(name = "library-books")
public interface BookClient {

	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> getBooks();

	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDto> getBook(@PathVariable(value = "book_id") int bookId);

	@PostMapping("/books")
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto);

	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable(value = "book_id") int bookId, @RequestBody BookDto bookDto);

	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<String> deleteBook(@PathVariable(value = "book_id") int bookId);
}
