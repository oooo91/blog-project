package com.portfolio.postproject.components.common;

import com.portfolio.postproject.exception.user.InvalidDtoException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

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