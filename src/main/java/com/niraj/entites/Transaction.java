package com.niraj.entites;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.niraj.util.BigDecimalConstant;
import com.niraj.validator.FutureDateConstraint;
import com.niraj.validator.OlderDateConstraint;
import com.niraj.validator.ValidUtcDateConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Getter
	private BigDecimal amount;

	@Getter
	@Setter
	private int transactionId;

	@ValidUtcDateConstraint(groups = ValidUtcDateConstraint.class)
	@FutureDateConstraint(groups = FutureDateConstraint.class)
	@OlderDateConstraint(groups = OlderDateConstraint.class)
	@Size
	@Getter
	@Setter
	private ZonedDateTime timestamp;

	public void setAmount(BigDecimal amount) {
		this.amount = amount.setScale(BigDecimalConstant.TRANSACTIONS_SCALE, BigDecimalConstant.ROUND_HALF_UP);
	}

}
