package com.portfolio.postproject.components.common;

import com.portfolio.postproject.exception.user.InvalidDtoException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationComponent {

	public void validation(Errors error) {
		if (error.hasErrors()) {
			List<String> errors = error.getAllErrors().stream().map(
					DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList());
			throw new InvalidDtoException(errors.get(0));
		}
	}
}