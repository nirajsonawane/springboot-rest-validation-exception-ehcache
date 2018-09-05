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
public class TransactionControllerFutureDateValidationTest {

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
	public void shouldReturnErrorCode422ForFutureDate_TransactionTimeplus2Seconds() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(),Mockito.anyInt());

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.plusSeconds(2)
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
	public void shouldCreateTransaction_DiffLessThan1Seconds() throws Exception {

		Mockito.doNothing()
				.when(transactionService)
				.addTransaction(Mockito.any(),Mockito.anyInt());

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.plusSeconds(1)
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
				.andExpect(status().isCreated());

	}


}
