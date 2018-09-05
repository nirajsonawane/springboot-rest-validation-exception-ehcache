package com.niraj.unit.service;

import java.util.ArrayList;

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
import com.niraj.util.BigDecimalConstant;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StatisticsServiceTest {

	@MockBean
	private CacheService cacheService;

	@Autowired
	private StatisticsService statisticsService;

	@Test
	public void shuldReturnStatistic_OnEmptyTransactionList() {

		Mockito.when(cacheService.getAllValuesFromCache(Mockito.anyString()))
				.thenReturn(new ArrayList<Transaction>());

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

}
