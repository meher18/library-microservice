package com.epam.clients;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFallBackFactory implements FallbackFactory<UserClient>{

	
	@Override
	public UserClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new UserClientImpl(cause);
	}

}
