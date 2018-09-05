package com.niraj.unit.util;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.niraj.Application;
import com.niraj.entites.Transaction;
import com.niraj.util.IsTransactionApplicableForStatistics;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class IsTransactionApplicableForStatisticsTest {

	
	private IsTransactionApplicableForStatistics condition ;
	
	
	@Test
	public void shouldReturnTrueForAll()
	{
		ZonedDateTime currentTime  = ZonedDateTime.now();
		Long allowedDefference = 60l;
		condition = new IsTransactionApplicableForStatistics(currentTime, allowedDefference);
		
		IntStream.range(1, 60).forEach(number->{
			Transaction t = Transaction.builder().timestamp(currentTime.minusSeconds(number)).build();
			Assert.assertTrue(condition.test(t));			
		});		
		
		
		
		
	}
	
	@Test
	public void shouldReturnFalse()
	{
		ZonedDateTime currentTime  = ZonedDateTime.now();
		Long allowedDefference = 60l;
		condition = new IsTransactionApplicableForStatistics(currentTime, allowedDefference);
		Transaction t = Transaction.builder().timestamp(currentTime.minusSeconds(61)).build();
		Assert.assertFalse(condition.test(t));			
				
		
	}
}
