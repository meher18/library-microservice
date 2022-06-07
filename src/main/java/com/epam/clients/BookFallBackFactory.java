package com.epam.clients;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class BookFallBackFactory implements FallbackFactory<BookClient>{

	@Override
	public BookClient create(Throwable cause) {
		return new BookClientImpl(cause);
	}

}
