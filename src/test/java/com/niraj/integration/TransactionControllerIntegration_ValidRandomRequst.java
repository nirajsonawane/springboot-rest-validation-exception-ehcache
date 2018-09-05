package com.niraj.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.niraj.TransactionObjectJson;
import com.niraj.controller.StatisticsController;
import com.niraj.entites.Statistic;
import com.niraj.util.BigDecimalConstant;

public class TransactionControllerIntegration_ValidRandomRequst extends BaseIntegrationTest {

	Logger logger = LoggerFactory.getLogger(StatisticsController.class);

	protected TransactionObjectJson transaction;

	private Statistic statistic;

	@Test
	public void shouldAcceptAll50Transactions() throws Exception {

		deleteTrans();
		IntStream.range(1, 51)
				.forEach(number -> {

					transaction = TransactionObjectJson.builder()
							.amount(BigDecimalConstant.getDefaultBigDecimal())
							.timestamp(ZonedDateTime.now()
									.toString())
							.build();
					String jsonString;
					try {
						jsonString = jacksonJsonBilderForTransaction.write(transaction)
								.getJson();
						MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

						logger.info("Request {}  : {}", number, jsonString);
						mockMvc.perform(accept)
								.andExpect(status().isCreated());

					} catch (Exception e) {
						e.printStackTrace();
						Assert.fail("No Exception expected");
					}

				});

		MockHttpServletRequestBuilder requestStatistics = getStatisticsGetRequest();

		statistic = getStatisticsObjectWithTradeCount(50);

		String json = jacksonJsonBilderForStatistics.write(statistic)
				.getJson();

		mockMvc.perform(requestStatistics)
				.andExpect(status().isOk())
				.andExpect(content().string(json));

	}

	@Test
	public void shouldDeleteAllTransactions() throws Exception {

		IntStream.range(1, 51)
				.forEach(number -> {

					transaction = TransactionObjectJson.builder()
							.amount(BigDecimalConstant.getDefaultBigDecimal())
							.timestamp(ZonedDateTime.now()
									.toString())
							.build();
					String jsonString;
					try {
						jsonString = jacksonJsonBilderForTransaction.write(transaction)
								.getJson();
						MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

						logger.info("Request {}  : {}", number, jsonString);
						mockMvc.perform(accept)
								.andExpect(status().isCreated());

					} catch (Exception e) {
						Assert.fail("No Exception expected");
					}

				});

		deleteTrans();

		MockHttpServletRequestBuilder requestStatistics = getStatisticsGetRequest();

		statistic = getStatisticsObjectWithTradeCount(0);

		String json = jacksonJsonBilderForStatistics.write(statistic)
				.getJson();

		mockMvc.perform(requestStatistics)
				.andExpect(status().isOk())
				.andExpect(content().string(json));

	}

	@Test
	public void shouldNotConsiderTransactionOlderThan60SecondsForStatistics() throws Exception {

		deleteTrans();

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.toString())
				.build();
		String jsonString;
		try {
			jsonString = jacksonJsonBilderForTransaction.write(transaction)
					.getJson();
			MockHttpServletRequestBuilder accept = getPostRequest(jsonString);
			mockMvc.perform(accept)
					.andExpect(status().isCreated());

			MockHttpServletRequestBuilder requestStatistics = getStatisticsGetRequest();
			statistic = getStatisticsObjectWithTradeCount(0);
			logger.info("Sleeping for 60s ");
			Thread.sleep(60 * 1000);

			String json = jacksonJsonBilderForStatistics.write(statistic)
					.getJson();

			mockMvc.perform(requestStatistics)
					.andExpect(status().isOk())
					.andExpect(content().string(json));

		} catch (Exception e) {
			Assert.fail("No Exception expected");
		}

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

}
