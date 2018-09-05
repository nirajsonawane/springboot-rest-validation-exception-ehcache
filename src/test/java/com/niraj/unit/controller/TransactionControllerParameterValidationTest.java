package com.niraj.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niraj.TransactionObjectJson;
import com.niraj.controller.TransactionController;
import com.niraj.service.TransactionService;
import com.niraj.util.BigDecimalConstant;
import com.niraj.util.TransactionIdGenerator;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionControllerParameterValidationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	@MockBean
	private TransactionIdGenerator transactionIdGenerator;
	private static final String NO_CONTENT = "";

	private static final String TRANSACTION_URI = "/transactions";

	private JacksonTester<TransactionObjectJson> jacksonJsonBilderForTransaction;

	private TransactionObjectJson transaction;

	@Before
	public void setup() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void shouldReturnErrorCode422ForEmptyAmount() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(), Mockito.anyInt());

		transaction = TransactionObjectJson.builder()
				.timestamp(ZonedDateTime.now()
						.minusSeconds(5)
						.toString())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void shouldReturnErrorCode422ForEmptyTimeStamp() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(), Mockito.anyInt());

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void shouldReturnErrorCode422ForInvalidAmount() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(), Mockito.anyInt());

		String jsonString = "{\"amount\":\"i am bad\",\"timestamp\":\"2018-08-26T20:55:23.591+05:30[Asia/Calcutta]\"}";
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isUnprocessableEntity());

	}

	@Test
	public void shouldReturnErrorCode422ForInvalidDate() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(), Mockito.anyInt());

		String jsonString = "{\"amount\":0.00,\"timestamp\":\"i am bad\"}";
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isUnprocessableEntity());

	}

}
