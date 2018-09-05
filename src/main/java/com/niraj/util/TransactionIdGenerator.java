package com.niraj.util;

import org.springframework.stereotype.Component;

@Component
public class TransactionIdGenerator {

	public int getTransaction() {
		return (int) (Math.random() * ((Integer.MAX_VALUE - 10) + 1)) + 10;

	}

}
