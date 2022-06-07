package com.epam.clients;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.dto.UserDto;

@FeignClient(name = "library-users", fallbackFactory = UserFallBackFactory.class)
@LoadBalancerClient(name = "library-users")
public interface UserClient {

	@GetMapping("/users/{username}")
	public ResponseEntity<UserDto> getUser(@PathVariable String username);

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAll();

	@PostMapping("/users")
	public ResponseEntity<UserDto> newUser(@RequestBody UserDto userDto);

	@PutMapping("/users/{username}")
	public ResponseEntity<UserDto> update(@PathVariable String username, @Valid @RequestBody UserDto userDto);

	@DeleteMapping("/users/{username}")
	public ResponseEntity<String> delete(@PathVariable String username);
}
