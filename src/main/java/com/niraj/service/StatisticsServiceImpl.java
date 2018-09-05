package com.niraj.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.niraj.cache.CacheService;
import com.niraj.entites.Statistic;
import com.niraj.entites.Transaction;
import com.niraj.util.DateUtils;
import com.niraj.util.IsTransactionApplicableForStatistics;
import com.niraj.util.TransactionSummaryCollector;
/**
 * 
 * @author Niraj Sonawane
 *
 */
@Component
public class StatisticsServiceImpl implements StatisticsService {
	
	Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);
	
	@Value("${transaction.timestamp.allowed.difference}")
	private Long allowedDefference;
	
	@Value("${transaction.cache.name}")
	private String cacheName;


	@Autowired
	private CacheService cacheService;
	
	/**
	 * Algorithm to get Statistics 
	 * 
	 * Step 1 : Reads all transactions available in
	 * cache.cache is setup to expire values after every 60 seconds (Not the request
	 * timeStamp),from the time it was added 
	 * 
	 * Step 2 : Filter transactions available in cache which are older that 60 seconds.	 * 
	 * Step 3 : Pass all available transctions to TransactionSummaryCollector to get Min,Max,Sum,Avg & Count
	 */
	@Override
	public Statistic getStatistics() {
		
		

		List<Transaction> statisticsApplicableTransactionsFromCache = cacheService.getAllValuesFromCache(cacheName);
		
		TransactionSummaryCollector transactionSummaryCollector = statisticsApplicableTransactionsFromCache
				.stream()
				.filter(new IsTransactionApplicableForStatistics(DateUtils.getCurrentZonedDateInUtc(),allowedDefference))
				.map(trans -> trans.getAmount())
				.collect(TransactionSummaryCollector.transactionSummaryStatistics());

		Statistic statistic = Statistic.builder()
				.avg(transactionSummaryCollector.getAverage().toString())
				.count(transactionSummaryCollector.getTotalTransactions())
				.max(transactionSummaryCollector.getMaximimTransactionAmount().toString())
				.min(transactionSummaryCollector.getMinimumTransactionAmount().toString())
				.sum(transactionSummaryCollector.getTotalTransactionSum().toString())
				.build();

		return statistic;

	}
	
}
