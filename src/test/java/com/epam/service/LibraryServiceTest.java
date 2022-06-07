package com.epam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dto.LibraryDto;
import com.epam.entity.Library;
import com.epam.repository.LibraryRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class LibraryServiceTest {

	@Autowired
	LibraryService libraryService;

	@Mock
	LibraryRepository libraryRepository;

	@Autowired
	ModelMapper mapper;

	@BeforeEach
	void setUp() {
		libraryService.mapper = mapper;
		libraryService.libraryRepository = libraryRepository;
	}

	@Test
	void issueBookTest() {
		Library library = new Library();
		when(libraryRepository.save(any())).thenReturn(library);
		assertNotNull(libraryService.issueBook(new LibraryDto()));
	}

	@Test
	void releaseBook() {

		LibraryDto libraryDto = new  LibraryDto();
		libraryDto.setUsername("name");
		libraryDto.setBookId(0);
		libraryService.releaseBook(libraryDto);
		verify(libraryRepository, times(1)).deleteAllByUsernameAndBookId(anyString(), anyInt());
	}

	@Test
	void getAllByUsername() {

		libraryService.releaseBook(new LibraryDto());
		List<Library> list = new ArrayList<>();
		list.add(new Library());
		when(libraryRepository.findAllByUsername(anyString())).thenReturn(list);
		
		assertEquals(1, libraryService.getAllByUsername("username").size());
	}
	
	
	

}
