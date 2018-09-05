package com.niraj.unit.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.niraj.Application;
import com.niraj.cache.CacheService;
import com.niraj.entites.Statistic;
import com.niraj.entites.Transaction;
import com.niraj.service.StatisticsService;
import com.niraj.service.TransactionService;
import com.niraj.util.BigDecimalConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StatisticsService_shouldSummarizeTransactionsCorrectly {

	@MockBean
	private CacheService cacheService;

	@Autowired
	private StatisticsService statisticsService;

	@Test
	public void shouldCalculateSumMaxMinAvgCount() {

		List<Transaction> list = new ArrayList<>();

		IntStream.range(1, 25)
				.forEach(num -> {

					Transaction trans = Transaction.builder()
							.amount(new BigDecimal(num).setScale(BigDecimalConstant.TRANSACTIONS_SCALE,
									BigDecimalConstant.ROUND_HALF_UP))
							.timestamp(ZonedDateTime.now()
									.minusSeconds(num))
							.build();
					list.add(trans);

				});

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(list);

		Statistic actual = statisticsService.getStatistics();

		Statistic expected = Statistic.builder()
				.sum("300.00")
				.avg("12.50")
				.max("24.00")
				.min("1.00")
				.count(24)
				.build();
		Assert.assertEquals(expected, actual);

	}

}
