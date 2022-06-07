package com.epam.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.entity.Library;

public interface LibraryRepository extends CrudRepository<Library, Integer> {

	public List<Library> findAllByUsername(String username);
	
	@Transactional
	public void deleteAllByUsernameAndBookId(String username, int bookId);
	
	@Transactional
	public void deleteAllByBookId(int bookId);
	
	@Transactional
	public void deleteAllByUsername(String username);
	
	public boolean existsByUsernameAndBookId(String username, int bookId);
}
