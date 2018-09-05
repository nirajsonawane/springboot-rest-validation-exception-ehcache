package com.niraj.util;

import java.math.BigDecimal;
import java.util.function.Consumer;
import java.util.stream.Collector;

import lombok.Data;
/**
 * 
 * @author Niraj Sonawane
 * 
 * Responsible for creating Summary of Transaction. This class is similar to SummaryInt. 
 *
 */
@Data
public class TransactionSummaryCollector implements Consumer<BigDecimal> {

	private BigDecimal totalTransactionSum = BigDecimalConstant.getDefaultBigDecimal();
	private BigDecimal minimumTransactionAmount = BigDecimalConstant.getDefaultBigDecimal();
	private BigDecimal maximimTransactionAmount = BigDecimalConstant.getDefaultBigDecimal();
	private int totalTransactions;

	public static Collector<BigDecimal, ?, TransactionSummaryCollector> transactionSummaryStatistics() {

		return Collector.of(TransactionSummaryCollector::new, TransactionSummaryCollector::accept,
				TransactionSummaryCollector::merge);
	}

	@Override
	public void accept(BigDecimal t) {

		if (totalTransactions == 0) {
			firstElementSetup(t);
		} else {
			totalTransactionSum = totalTransactionSum.add(t);
			minimumTransactionAmount = minimumTransactionAmount.min(t);
			maximimTransactionAmount = maximimTransactionAmount.max(t);
			totalTransactions++;
		}
	}

	public TransactionSummaryCollector merge(TransactionSummaryCollector s) {
		if (s.totalTransactions > 0) {
			if (totalTransactions == 0) {
				setupFirstElement(s);
			} else {
				totalTransactionSum = totalTransactionSum.add(s.totalTransactionSum);
				minimumTransactionAmount = minimumTransactionAmount.min(s.minimumTransactionAmount);
				maximimTransactionAmount = maximimTransactionAmount.max(s.maximimTransactionAmount);
				totalTransactions += s.totalTransactions;
			}
		}
		return this;
	}

	private void setupFirstElement(TransactionSummaryCollector s) {
		totalTransactions = s.totalTransactions;
		totalTransactionSum = s.totalTransactionSum;
		minimumTransactionAmount = s.minimumTransactionAmount;
		maximimTransactionAmount = s.maximimTransactionAmount;
	}

	private void firstElementSetup(BigDecimal t) {
		totalTransactions = 1;
		totalTransactionSum = t;
		minimumTransactionAmount = t;
		maximimTransactionAmount = t;
	}

	public BigDecimal getAverage() {
		if (totalTransactions == 0) {
			return BigDecimalConstant.getDefaultBigDecimal();
		}
		return totalTransactionSum.divide(BigDecimal.valueOf(totalTransactions), BigDecimalConstant.TRANSACTIONS_SCALE,
				BigDecimalConstant.ROUND_HALF_UP);

	}

}
