package com.niraj;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionObjectJson {

	private BigDecimal amount;
	private String timestamp;
}
