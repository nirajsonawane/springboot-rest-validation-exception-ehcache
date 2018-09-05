package com.niraj.unit.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.niraj.Application;
import com.niraj.entites.Transaction;
import com.niraj.service.TransactionService;
import com.niraj.util.TransactionIdGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@DirtiesContext
public class TransactionServiceTest {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private TransactionIdGenerator transactionIdGenerator; 

	@Test
	public void shouldAddTransaction() {
		transactionService.addTransaction(Transaction.builder()
				.build(),transactionIdGenerator.getTransaction());
		Assert.assertEquals(1, transactionService.getAllTransactions()
				.size());
		transactionService.deleteAllTransactions();
	}

	@Test
	public void shouldReturnAllTransaction() {
		transactionService.deleteAllTransactions();
		Assert.assertEquals(0, transactionService.getAllTransactions()
				.size());
		transactionService.addTransaction(Transaction.builder()
				.build(),transactionIdGenerator.getTransaction());
		Assert.assertEquals(1, transactionService.getAllTransactions()
				.size());
		transactionService.deleteAllTransactions();
	}

	@Test
	public void shouldDeleteAllTransaction() {
		transactionService.deleteAllTransactions();
		Assert.assertEquals(0, transactionService.getAllTransactions()
				.size());
		transactionService.addTransaction(Transaction.builder()
				.build(),transactionIdGenerator.getTransaction());
		transactionService.deleteAllTransactions();
		Assert.assertEquals(0, transactionService.getAllTransactions()
				.size());
	}

}
