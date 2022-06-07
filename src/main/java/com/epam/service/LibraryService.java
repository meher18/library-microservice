package com.epam.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.epam.dto.LibraryDto;
import com.epam.entity.Library;
import com.epam.exceptions.BookLimitExceededException;
import com.epam.exceptions.BookNotMappedToUser;
import com.epam.repository.LibraryRepository;
import com.epam.util.Constants;

@Controller
public class LibraryService {

	@Autowired
	ModelMapper mapper;

	@Autowired
	LibraryRepository libraryRepository;

	public LibraryDto issueBook(LibraryDto libraryDto) {
		Library librarySaved;
		if (libraryRepository.findAllByUsername(libraryDto.getUsername()).size() < 3) {
			librarySaved = libraryRepository.save(mapper.map(libraryDto, Library.class));
		} else {
			throw new BookLimitExceededException(Constants.USER_BOOK_LIMIT);
		}
		return mapper.map(librarySaved, LibraryDto.class);
	}

	public void deleteAllByBookId(int bookId) {
		libraryRepository.deleteAllByBookId(bookId);
	}

	public void deleteAllByUsername(String username) {
		libraryRepository.deleteAllByUsername(username);
	}

	public void releaseBook(LibraryDto libraryDto) {

		if (libraryRepository.existsByUsernameAndBookId(libraryDto.getUsername(), libraryDto.getBookId())) {
			libraryRepository.deleteAllByUsernameAndBookId(libraryDto.getUsername(), libraryDto.getBookId());
		} else {
			throw new BookNotMappedToUser(Constants.INVALID_LIBRARY_DATA);
		}
	}

	public List<LibraryDto> getAllByUsername(String username) {
		return mapper.map(libraryRepository.findAllByUsername(username), new TypeToken<List<LibraryDto>>() {
		}.getType());
	}

}
