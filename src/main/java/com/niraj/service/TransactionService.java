package com.niraj.service;

import java.util.List;

import com.niraj.entites.Transaction;

public interface TransactionService {

	void addTransaction(Transaction transaction,int transactionId);

	void deleteAllTransactions();

	public List<Transaction> getAllTransactions();

}
