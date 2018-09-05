package com.niraj.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.BeginState;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.niraj.TransactionObjectJson;
import com.niraj.entites.Statistic;
import com.niraj.util.BigDecimalConstant;

public class TransactionControllerIntegration_TransCollectorTest extends BaseIntegrationTest {

	Logger logger = LoggerFactory.getLogger(TransactionControllerIntegration_TransCollectorTest.class);

	protected TransactionObjectJson transaction;

	private Statistic statistic;

	@Test
	public void shouldProduceCorrectStatsForSumCountMinMax() throws Exception {

		BigDecimal sum = BigDecimalConstant.getDefaultBigDecimal();
		BigDecimal min = BigDecimalConstant.getDefaultBigDecimal();
		BigDecimal max = BigDecimalConstant.getDefaultBigDecimal();
		BigDecimal avg = BigDecimalConstant.getDefaultBigDecimal();

		deleteTrans();
		for (int i = 0; i < 50; i++) {
			BigDecimal randomNumber = getRandomNumber(
					new BigDecimal(10.00).setScale(2, BigDecimalConstant.ROUND_HALF_UP),
					new BigDecimal(100.00).setScale(2, BigDecimalConstant.ROUND_HALF_UP));
			if (i == 0) {
				min = randomNumber;
				max = randomNumber;
			}

			sum = sum.add(randomNumber);
			min = min.min(randomNumber);
			max = max.max(randomNumber);


			transaction = TransactionObjectJson.builder()
					.amount(randomNumber)
					.timestamp(ZonedDateTime.now()
							.toString())
					.build();
			String jsonString;
			try {
				jsonString = jacksonJsonBilderForTransaction.write(transaction)
						.getJson();
				MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

				logger.info("Request {}  : {}", i, jsonString);
				mockMvc.perform(accept)
						.andExpect(status().isCreated());

			} catch (Exception e) {
				Assert.fail("No Exception expected");
			}

		}

		MockHttpServletRequestBuilder requestStatistics = getStatisticsGetRequest();

		statistic = Statistic.builder()
				.min(min.toString())
				.max(max.toString())
				.sum(sum.toString())
				.count(50)
				.avg(sum.divide(new BigDecimal("50"))
						.setScale(BigDecimalConstant.TRANSACTIONS_SCALE, BigDecimalConstant.ROUND_HALF_UP)
						.toString())
				.build();

		String json = jacksonJsonBilderForStatistics.write(statistic)
				.getJson();

		mockMvc.perform(requestStatistics)
				.andExpect(status().isOk())
				.andExpect(content().string(json));
	}

	private void deleteTrans() throws Exception {
		MockHttpServletRequestBuilder requestdelete = getDeleteRequest();
		mockMvc.perform(requestdelete)
				.andExpect(status().isNoContent());
	}

	private Statistic getStatisticsObjectWithTradeCount(int cnt) {
		return Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(cnt)
				.build();
	}

	private MockHttpServletRequestBuilder getStatisticsGetRequest() {
		return MockMvcRequestBuilders.get(STATISTICS_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
	}

	private MockHttpServletRequestBuilder getPostRequest(String jsonString) {
		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		return accept;
	}

	private MockHttpServletRequestBuilder getDeleteRequest() {
		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.delete(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);
		return accept;
	}

	private static BigDecimal getRandomNumber(BigDecimal min, BigDecimal max) {
		return min.add(new BigDecimal(Math.random()))
				.multiply(max.subtract(min))
				.setScale(2, BigDecimalConstant.ROUND_HALF_UP);
	}
}
