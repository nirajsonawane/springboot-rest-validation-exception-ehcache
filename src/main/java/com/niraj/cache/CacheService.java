package com.niraj.cache;

import java.util.ArrayList;
import java.util.List;

import javax.cache.Cache;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.niraj.entites.Transaction;
/**
 * 
 * @author Niraj Sonawane
 * 
 * This class is responsible for reading all values available in cache
 */
@Component
public class CacheService {

	@Autowired
	private CacheManager cacheManager;

	public List<Transaction> getAllValuesFromCache(final String cacheName) {

		Cache<Object, Transaction> cache = cacheManager.getCache(cacheName);

		List<Transaction> transactionList = new ArrayList<>();
		for (Cache.Entry<Object, Transaction> entry : cache) {
			if (null != entry)
				transactionList.add(entry.getValue());
		}
		return transactionList;

	}

}
