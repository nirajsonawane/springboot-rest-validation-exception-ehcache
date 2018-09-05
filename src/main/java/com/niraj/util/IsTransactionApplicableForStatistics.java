package com.niraj.util;

import java.time.ZonedDateTime;
import java.util.function.Predicate;

import com.niraj.entites.Transaction;
/**
 * 
 * @author Niraj Sonawane
 *
 * Predicate to check TransactionApplicable as per rule
 */
public class IsTransactionApplicableForStatistics implements Predicate<Transaction> {

	private Long allowedDefference;

	private ZonedDateTime currentDate;

	public IsTransactionApplicableForStatistics(ZonedDateTime currentDate, Long allowedDefference) {
		this.currentDate = currentDate;
		this.allowedDefference = allowedDefference;
	}

	@Override
	public boolean test(Transaction transaction) {

		return DateUtils.isWithInSeconds(this.currentDate, transaction.getTimestamp(), allowedDefference);
	}

}
