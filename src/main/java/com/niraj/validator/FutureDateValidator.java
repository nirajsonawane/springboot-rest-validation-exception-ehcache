package com.niraj.validator;

import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.niraj.exception.FutureTransactionDateException;
import com.niraj.util.DateUtils;
/**
 * 
 * @author Niraj Sonawane
 * 
 * Responsible for validating timeStamp for future date.     
 *
 */
public class FutureDateValidator implements ConstraintValidator<FutureDateConstraint, ZonedDateTime> {

	@Override
	public void initialize(FutureDateConstraint futureDateConstraint) {

	}

	@Override
	public boolean isValid(ZonedDateTime date, ConstraintValidatorContext context) {

		if (DateUtils.isGreaterThanCurrentUtc(date)) {
			throw new FutureTransactionDateException("Date is not currnet");
		}

		return true;
	}

}
