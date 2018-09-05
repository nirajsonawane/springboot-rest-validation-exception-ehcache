package com.niraj.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemoveAll;
import javax.cache.annotation.CacheValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.niraj.controller.TransactionController;
import com.niraj.entites.Transaction;
/**
 * 
 * @author Niraj Sonawane
 * 
 * Responsible for adding Transactions to transactions
 * Class is configure to add/Delete values in  cache also.
 *   
 */
@Component
@CacheDefaults(cacheName = "transaction")
public class TransactionServiceImpl implements TransactionService {

	Logger logger = LoggerFactory.getLogger(TransactionController.class);
	
	
	private List<Transaction> transactions;

	@PostConstruct
	public void setup() {
		this.transactions = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	@CachePut(cacheName = "transaction")
	public void addTransaction(@CacheValue Transaction transaction, @CacheKey int transactionId) {
		this.transactions.add(transaction);

	}

	@Override
	@CacheRemoveAll
	public void deleteAllTransactions() {
		this.transactions.clear();

	}

	@Override
	public List<Transaction> getAllTransactions() {
		return this.transactions;
	}

}
