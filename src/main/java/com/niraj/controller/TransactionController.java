package com.niraj.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.niraj.entites.Transaction;
import com.niraj.service.TransactionService;
import com.niraj.util.TransactionIdGenerator;
import com.niraj.validator.DateChecks;

/**
 * 
 * @author Niraj Sonawane
 *  Rest controller responsible for accepting Post &  DELETE transactions request
 * 
 */
@RestController
@RequestMapping("/transactions")
public class TransactionController {

	Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService trasactionService;

	@Autowired
	private TransactionIdGenerator transactionIdGenerator;

	@PostMapping
	public ResponseEntity<Void> addTransaction(@Validated(DateChecks.class) @RequestBody Transaction transaction) {

		int transactionId = generateTransactionId(transaction);

		trasactionService.addTransaction(transaction, transactionId);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
				.build()
				.toUri())
				.build();
	}

	/**
	 * 
	 * @param transaction
	 * @return
	 * 
	 * 		Method is responsible for creating unique transactionId for each
	 *         valid request. Unique key will also help to avoid two transaction
	 *         with same time stamp & amount being consider as duplicate.
	 * 
	 */
	private int generateTransactionId(Transaction transaction) {
		int transactionId = transactionIdGenerator.getTransaction();
		transaction.setTransactionId(transactionId);
		return transactionId;
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllTransactions() {

		logger.info("Delete Transaction request");
		trasactionService.deleteAllTransactions();
		return ResponseEntity.noContent()
				.build();
	}

}
