package com.niraj.cache;

import java.util.concurrent.TimeUnit;

import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Niraj
 *
 *This class is responsible for creating the Transaction Cache. Cache is Using CreatedExpiryPolicy expiration policy.
 *As per this policy The new added value will have availability till 60 seconds.
 *
 *Note : StatisticsService has additional filtering based on timestamp.  
 *
 */
@Component
public class CacheSetup  implements JCacheManagerCustomizer {
	
	
	
	@Value("${transaction.cache.name}")
	private String cacheName;
	
	@Override
	public void customize(CacheManager cacheManager) {
		if(null==cacheManager.getCache(cacheName))
		{
			cacheManager.createCache(cacheName,
					new MutableConfiguration<>()
					.setManagementEnabled(false)
					.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 60)))
					.setStoreByValue(false)							
					.setStatisticsEnabled(true));
			
		}
	}

}
