package com.niraj.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZonedDateTime;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.niraj.TransactionObjectJson;
import com.niraj.util.BigDecimalConstant;


public class TransactionControllerIntegration_ValidationAndExceptionHandlerTest extends BaseIntegrationTest {

	protected TransactionObjectJson transaction;
	
	@Test
	public void shouldReturnErrorCode422ForFutureDate_DifferenceMoreThan1Second() throws Exception {

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.plusSeconds(2)
						.toString())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isUnprocessableEntity());

	}

	private MockHttpServletRequestBuilder getPostRequest(String jsonString) {
		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.post(TRANSACTION_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonString);
		return accept;
	}

	@Test
	public void shouldCreateTransaction_DiffLessThan1Seconds() throws Exception {

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.plusSeconds(1)
						.toString())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isCreated());

	}

	@Test
	public void shouldReturnResponeCode204_TransactionTimeMinus61Seconds() throws Exception {

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.minusSeconds(61)
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
				.andExpect(status().isNoContent());

	}

	@Test
	public void shouldReturnResponeCode204_TransactionTimeMinus60Seconds() throws Exception {

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.minusSeconds(60)
						.toString())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);

		mockMvc.perform(accept)
				.andExpect(status().isNoContent());

	}

	@Test
	public void shouldCreateTransaction_TimeMinus59Seconds() throws Exception {

		transaction = TransactionObjectJson.builder()
				.amount(BigDecimalConstant.getDefaultBigDecimal())
				.timestamp(ZonedDateTime.now()
						.minusSeconds(59)
						.toString())
				.build();

		String jsonString = jacksonJsonBilderForTransaction.write(transaction)
				.getJson();
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);
		mockMvc.perform(accept)
				.andExpect(status().isCreated());

	}
	
	@Test
	public void shouldReturn400ForInvalidJson() throws Exception {

		 

		String jsonString = " {\r\n" + 
				"  \"amount\": \"12.3343\",xzxvx x \r\n" + 
				"  \"timestamp\": \"2018-07-17T09:59:51.312Z\"\r\n" + 
				"}";
				
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);
		mockMvc.perform(accept)
				.andExpect(status().isBadRequest());

	}
	
	@Test
	public void shouldReturn400ForInvalidJson2() throws Exception {

		 

		String jsonString = " {\r\n" + 
				" 	\"amount\": \"12.3343\"\r\n" + 
				" 	\"timestamp\": \"2018-07-17T09:59:51.312Z\"\r\n" + 
				" }";
				
		System.out.println(jsonString);

		MockHttpServletRequestBuilder accept = getPostRequest(jsonString);
		mockMvc.perform(accept)
				.andExpect(status().isBadRequest());

	}


}
