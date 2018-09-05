package com.niraj.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalConstant {

	public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;

	public static final Integer TRANSACTIONS_SCALE = 2;
	
	public static final MathContext TRANSACTIONS_MATH_CONTEXT = new MathContext(2, RoundingMode.HALF_UP);

	public static BigDecimal getDefaultBigDecimal() {
		return BigDecimal.ZERO.setScale(TRANSACTIONS_SCALE, ROUND_HALF_UP);

	}

}
