package com.niraj.unit.util;

import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.niraj.util.DateUtils;

public class DateUtilsTest {


	@Test
	public void testIsGreaterThanCurrentUtc()
	{
		
		Assert.assertFalse(DateUtils.isGreaterThanCurrentUtc(ZonedDateTime.now().minusSeconds(1)));
		Assert.assertTrue(DateUtils.isGreaterThanCurrentUtc(ZonedDateTime.now().plusSeconds(2)));
		Assert.assertTrue(DateUtils.isGreaterThanCurrentUtc(ZonedDateTime.now().plusSeconds(1)));
		Assert.assertFalse(DateUtils.isGreaterThanCurrentUtc(ZonedDateTime.now()));
	}
	
	@Test
	public void testIsOlderSecondsThanCurrentUTC()
	{
		Assert.assertFalse(DateUtils.isOlderSecondsThanCurrentUTC(ZonedDateTime.now(), 60l));
		Assert.assertTrue(DateUtils.isOlderSecondsThanCurrentUTC(ZonedDateTime.now().minusSeconds(61), 60l));
	}
	
	@Test
	public void testIsWithInSeconds()
	{
		Assert.assertTrue(DateUtils.isWithInSeconds(ZonedDateTime.now(), ZonedDateTime.now().minusSeconds(10), 60L));
		Assert.assertTrue(DateUtils.isWithInSeconds(ZonedDateTime.now(), ZonedDateTime.now().minusSeconds(59), 60L));
		Assert.assertFalse(DateUtils.isWithInSeconds(ZonedDateTime.now(), ZonedDateTime.now().minusSeconds(60), 60L));
		Assert.assertFalse(DateUtils.isWithInSeconds(ZonedDateTime.now(), ZonedDateTime.now().minusSeconds(61), 60L));
		
	}
}
