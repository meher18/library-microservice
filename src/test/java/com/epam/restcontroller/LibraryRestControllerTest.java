package com.epam.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.clients.BookClient;
import com.epam.clients.UserClient;
import com.epam.dto.BookDto;
import com.epam.dto.LibraryDto;
import com.epam.dto.UserDto;
import com.epam.service.LibraryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import feign.FeignException;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = LibraryRestController.class)
public class LibraryRestControllerTest {

	@Autowired
	LibraryRestController libraryRestController;

	@MockBean
	LibraryService libraryService;

	@MockBean
	UserClient userClient;

	@MockBean
	BookClient bookClient;

	@Autowired
	MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	void usersTest() throws Exception {
		ArrayList<UserDto> userDtos = new ArrayList<>();
		userDtos.add(new UserDto());
		ResponseEntity<List<UserDto>> list = new ResponseEntity<List<UserDto>>(userDtos, HttpStatus.OK);
		when(userClient.getAll()).thenReturn(list);
		mvc.perform(get("/library/users")).andExpect(status().isOk());
	}

	@Test
	void oneUserTest() throws Exception {
		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.OK);
		when(userClient.getUser(anyString())).thenReturn(user);
		mvc.perform(get("/library/users/user1")).andExpect(status().isOk());
	}

	@Test
	void newUserTest() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(1);
		userDto.setName("name");
		userDto.setEmail("msr@gmail.com");
		userDto.setUsername("name1");
		String userJson = new Gson().toJson(userDto);
		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.CREATED);
		when(userClient.newUser(any())).thenReturn(user);
		mvc.perform(post("/library/users").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(userJson)).andExpect(status().isCreated());
	}

	@Test
	void updateUserTest() throws Exception {
		UserDto userDto = new UserDto();
		userDto.setId(1);
		userDto.setName("user");
		userDto.setEmail("msr@gmail.com");
		userDto.setUsername("user1");
		String userJson = new Gson().toJson(userDto);
		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.CREATED);
		when(userClient.update(anyString(), any())).thenReturn(user);
		mvc.perform(put("/library/users/user1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(userJson)).andExpect(status().isCreated());
	}

	@Test
	void deleteUserTest() throws Exception {
		ResponseEntity<String> userResponse = new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
		when(userClient.delete(anyString())).thenReturn(userResponse);
		mvc.perform(delete("/library/users/user1")).andExpect(status().isNoContent());
	}

	@Test
	void booksTest() throws Exception {
		ArrayList<BookDto> bookDtos = new ArrayList<>();
		bookDtos.add(new BookDto());
		ResponseEntity<List<BookDto>> list = new ResponseEntity<>(bookDtos, HttpStatus.OK);
		when(bookClient.getBooks()).thenReturn(list);
		mvc.perform(get("/library/books")).andExpect(status().isOk());
	}

	@Test
	void oneBookTest() throws Exception {
		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.OK);
		when(bookClient.getBook(anyInt())).thenReturn(book);
		mvc.perform(get("/library/books/1")).andExpect(status().isOk());
	}

	@Test
	void newBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setName("name");
		bookDto.setPublisher("publisher");
		bookDto.setAuthor("author");
		String bookJson = new Gson().toJson(bookDto);
		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.CREATED);

		when(bookClient.addBook(any())).thenReturn(book);
		mvc.perform(post("/library/books").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isCreated());
	}

	@Test
	void updateBookTest() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setId(1);
		bookDto.setName("name");
		bookDto.setPublisher("publisher");
		bookDto.setAuthor("author");
		String bookJson = new Gson().toJson(bookDto);
		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.CREATED);

		when(bookClient.updateBook(anyInt(), any())).thenReturn(book);
		mvc.perform(put("/library/books/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson)).andExpect(status().isCreated());
	}

	@Test
	void deleteBookTest() throws Exception {
		ResponseEntity<String> bookResponse = new ResponseEntity<String>("Deleted", HttpStatus.NO_CONTENT);
		when(bookClient.deleteBook(anyInt())).thenReturn(bookResponse);
		mvc.perform(delete("/library/books/45345")).andExpect(status().isNoContent());
	}

	@Test
	void issueBookTest() throws Exception {
		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.OK);

		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.OK);

		when(userClient.getUser(anyString())).thenReturn(user);
		when(bookClient.getBook(anyInt())).thenReturn(book);

		when(libraryService.issueBook(any())).thenReturn(new LibraryDto());

		mvc.perform(post("/library/users/asf/books/1")).andExpect(status().isCreated());
	}

	@Test
	void releaseBookTest() throws Exception {
		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.OK);

		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.OK);

		when(userClient.getUser(anyString())).thenReturn(user);
		when(bookClient.getBook(anyInt())).thenReturn(book);

		mvc.perform(delete("/library/users/asf/books/1")).andExpect(status().isNoContent());
		verify(libraryService, times(1)).releaseBook(any());

	}

	@Test
	void feignClientExceptionTest() throws Exception {
//		ResponseEntity<UserDto> user = new ResponseEntity<UserDto>(new UserDto(), HttpStatus.OK);
//
//		ResponseEntity<BookDto> book = new ResponseEntity<BookDto>(new BookDto(), HttpStatus.OK);

//		doThrow(RuntimeException.class).
//		when(userClient.getUser(anyString())).thenThrow(new RuntimeException("user not found"));
//		when(bookClient.getBook(anyInt())).thenReturn(book);
//		mvc = MockMvcBuilders.standaloneSetup(libraryRestController)
//				.setControllerAdvice(new FeignExceptionHandler()).build();
//		mvc.perform(delete("/library/users/asf/books/1")).andExpect(status().());
//		mvc.perform(get("/library/users/sadf")).andExpect(status().isBadRequest());
//		verify(libraryService, times(1)).releaseBook(any());

	}

	@Test
	void getBookByIdTypeMismatch() throws Exception {
		BookDto bookDto = new BookDto();
		when(bookClient.getBook(anyInt())).thenReturn(new ResponseEntity<BookDto>(bookDto, HttpStatus.BAD_REQUEST));
		String bookInJson = mapper.writeValueAsString(bookDto);
		mvc.perform(get("/library/books/" + bookDto.getAuthor()).contentType(MediaType.APPLICATION_JSON)
				.content(bookInJson)).andExpect(status().isBadRequest());
	}

	@Test
	void testInsertInvalidBook() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setAuthor(null);
		when(bookClient.addBook(any())).thenThrow(FeignException.class);
		String bookInJson = mapper.writeValueAsString(bookDto);
		mvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON).content(bookInJson))
				.andExpect(status().isBadRequest());
	}
}
