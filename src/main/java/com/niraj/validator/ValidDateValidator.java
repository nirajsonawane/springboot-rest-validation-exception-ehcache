package com.niraj.validator;

import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.niraj.exception.ParamParserException;

public class ValidDateValidator implements ConstraintValidator<ValidUtcDateConstraint, ZonedDateTime> {

	@Override
	public void initialize(ValidUtcDateConstraint validUtcDateConstraint) {

	}

	@Override
	public boolean isValid(ZonedDateTime date, ConstraintValidatorContext context) {

		if (date == null)
			throw new ParamParserException("Invalid Date");

		return true;
	}

}
