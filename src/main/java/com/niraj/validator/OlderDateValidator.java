package com.niraj.validator;

import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.niraj.exception.OlderTransactionDateException;
import com.niraj.util.DateUtils;
/**
 * 
 * @author Niraj Sonawane
 * 
 * Responsible for validating date for older date constrain.   
 */
@Component
public class OlderDateValidator implements ConstraintValidator<OlderDateConstraint, ZonedDateTime> {

	@Value("${transaction.timestamp.allowed.difference}")
	private Long allowedDefference;

	@Override
	public void initialize(OlderDateConstraint olderDateConstraint) {

	}

	@Override
	public boolean isValid(ZonedDateTime date, ConstraintValidatorContext context) {

		if (DateUtils.isOlderSecondsThanCurrentUTC(date, allowedDefference)) {
			throw new OlderTransactionDateException("Transaction is older than 60 Seconds");
		}
		return true;

	}

}
