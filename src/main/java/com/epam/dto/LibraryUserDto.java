package com.epam.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryUserDto {

	int id;
	String username;
	String name;
	String email;
	List<BookDto> books;
}
