package com.niraj.unit.service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class StatisticsService_shouldConsiderTransactionsWithIn60Seconds {

	@MockBean
	private CacheService cacheService;

	@Autowired
	private StatisticsService statisticsService;

	@Test
	public void shouldNotConsiderTransaction_OlderThan61Seconds() {

		List<Transaction> list = new ArrayList<>();

		Transaction trans = Transaction.builder()
				.amount(new BigDecimal(122.22).setScale(BigDecimalConstant.TRANSACTIONS_SCALE,
						BigDecimalConstant.ROUND_HALF_UP))
				.timestamp(ZonedDateTime.now()
						.minusSeconds(61))
				.build();

		list.add(trans);

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(list);

		Statistic actual = statisticsService.getStatistics();

		Statistic expected = Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(0)
				.build();
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void shouldNotConsiderTransaction_OlderThan60Seconds() {

		List<Transaction> list = new ArrayList<>();

		Transaction trans = Transaction.builder()
				.amount(new BigDecimal(122.22).setScale(BigDecimalConstant.TRANSACTIONS_SCALE,
						BigDecimalConstant.ROUND_HALF_UP))
				.timestamp(ZonedDateTime.now()
						.minusSeconds(60))
				.build();

		list.add(trans);

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(list);

		Statistic actual = statisticsService.getStatistics();

		Statistic expected = Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(0)
				.build();
		Assert.assertEquals(expected, actual);

	}

	@Test
	public void shouldConsiderTransaction_OlderThan58Seconds() {

		List<Transaction> list = new ArrayList<>();

		Transaction trans = Transaction.builder()
				.amount(new BigDecimal(122.22).setScale(BigDecimalConstant.TRANSACTIONS_SCALE,
						BigDecimalConstant.ROUND_HALF_UP))
				.timestamp(ZonedDateTime.now()
						.minusSeconds(58))
				.build();

		list.add(trans);

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(list);

		Statistic actual = statisticsService.getStatistics();

		Statistic expected = Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(1)
				.build();
		Assert.assertEquals(expected.getCount(), actual.getCount());

	}

	@Test
	public void shouldConsiderTransaction_OlderThan59Seconds() {

		List<Transaction> list = new ArrayList<>();

		Transaction trans = Transaction.builder()
				.amount(new BigDecimal(122.22).setScale(BigDecimalConstant.TRANSACTIONS_SCALE,
						BigDecimalConstant.ROUND_HALF_UP))
				.timestamp(ZonedDateTime.now()
						.minusSeconds(59))
				.build();

		list.add(trans);

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(list);

		Statistic actual = statisticsService.getStatistics();

		Statistic expected = Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(1)
				.build();
		Assert.assertEquals(expected.getCount(), actual.getCount());

	}

}
