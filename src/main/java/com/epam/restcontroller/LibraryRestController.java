package com.epam.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.clients.BookClient;
import com.epam.clients.UserClient;
import com.epam.dto.BookDto;
import com.epam.dto.LibraryDto;
import com.epam.dto.LibraryUserDto;
import com.epam.dto.UserDto;
import com.epam.service.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryRestController {

	@Autowired
	BookClient bookClient;

	@Autowired
	UserClient userClient;

	@Autowired
	LibraryService libraryService;

	@GetMapping("/books")
	public ResponseEntity<List<BookDto>> getBooks() {
		return new ResponseEntity<>(bookClient.getBooks().getBody(), HttpStatus.OK);
	}

	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDto> getBook(@PathVariable(value = "book_id") int bookId) {
		return bookClient.getBook(bookId);
	}

	@PostMapping("/books")
	public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
		return bookClient.addBook(bookDto);
	}

	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDto> updateBook(@PathVariable(value = "book_id") int bookId,
			@RequestBody BookDto bookDto) {
		return bookClient.updateBook(bookId, bookDto);
	}

	@DeleteMapping("/books/{book_id}")
	public void deleteBook(@PathVariable(value = "book_id") int bookId) {
		libraryService.deleteAllByBookId(bookId);
		bookClient.deleteBook(bookId);
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<LibraryUserDto> getUser(@PathVariable String username) {

		LibraryUserDto libraryUserDto = new LibraryUserDto();

		libraryUserDto.setId(userClient.getUser(username).getBody().getId());
		libraryUserDto.setUsername(userClient.getUser(username).getBody().getUsername());
		libraryUserDto.setName(userClient.getUser(username).getBody().getName());
		libraryUserDto.setEmail(userClient.getUser(username).getBody().getEmail());
		libraryUserDto.setBooks(new ArrayList<>());
		libraryService.getAllByUsername(username)
				.forEach(library -> libraryUserDto.getBooks().add(bookClient.getBook(library.getBookId()).getBody()));
		return new ResponseEntity<>(libraryUserDto, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAll() {
		return userClient.getAll();
	}

	@PostMapping("/users")
	public ResponseEntity<UserDto> newUser(@RequestBody UserDto userDto) {
		return userClient.newUser(userDto);
	}

	@PutMapping("/users/{username}")
	public ResponseEntity<UserDto> update(@PathVariable String username, @Valid @RequestBody UserDto userDto) {
		return userClient.update(username, userDto);
	}

	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> delete(@PathVariable String username) {
		libraryService.deleteAllByUsername(username);
		return userClient.delete(username);
	}

	@PostMapping("/users/{username}/books/{book_id}")
	public ResponseEntity<LibraryDto> issueBook(@PathVariable(value = "username") String username,
			@PathVariable(value = "book_id") int bookId) {
		UserDto userDto = userClient.getUser(username).getBody();
		BookDto bookDto = bookClient.getBook(bookId).getBody();
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setUsername(userDto.getUsername());
		libraryDto.setBookId(bookDto.getId());

		return new ResponseEntity<>(libraryService.issueBook(libraryDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/users/{username}/books/{book_id}")
	public ResponseEntity<String> releaseBook(@PathVariable(value = "username") String username,
			@PathVariable(value = "book_id") int bookId) {

		UserDto userDto = userClient.getUser(username).getBody();
		BookDto bookDto = bookClient.getBook(bookId).getBody();
		LibraryDto libraryDto = new LibraryDto();
		libraryDto.setUsername(userDto.getUsername());
		libraryDto.setBookId(bookDto.getId());
		libraryService.releaseBook(libraryDto);

		return new ResponseEntity<>("Released", HttpStatus.NO_CONTENT);
	}
}
