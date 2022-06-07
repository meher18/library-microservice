package com.epam.clients;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.epam.dto.UserDto;

import feign.FeignException;

public class UserClientImpl implements UserClient {

	Throwable cause;

	public UserClientImpl(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResponseEntity<UserDto> getUser(String username) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		} else {
			return new ResponseEntity<>(new UserDto(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public ResponseEntity<List<UserDto>> getAll() {
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
	}

	@Override
	public ResponseEntity<UserDto> newUser(UserDto userDto) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		} else {
			return new ResponseEntity<>(new UserDto(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public ResponseEntity<UserDto> update(String username, @Valid UserDto userDto) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		} else {
			return new ResponseEntity<>(new UserDto(), HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@Override
	public ResponseEntity<String> delete(String username) {
		if (cause instanceof FeignException) {
			throw (FeignException) cause;
		} else {
			return new ResponseEntity<>("", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

}
