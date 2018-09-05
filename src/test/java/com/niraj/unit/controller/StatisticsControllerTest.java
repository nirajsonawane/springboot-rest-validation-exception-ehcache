
package com.niraj.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.niraj.controller.StatisticsController;
import com.niraj.entites.Statistic;
import com.niraj.service.StatisticsService;
import com.niraj.util.BigDecimalConstant;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StatisticsController.class, secure = false)
public class StatisticsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	

	@MockBean
	private StatisticsService statisticsService;

	private static final String NO_CONTENT = "";

	private static final String STATISTICS_URI = "/statistics";

	private JacksonTester<Statistic> jacksonJsonBilderForTransaction;

	private TransactionObjectJson transaction;

	private JacksonTester<Statistic> jacksonJsonBilderForStatistic;

	private Statistic statistic;

	@Before
	public void setup() {
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}

	@Test
	public void shouldReturnStatistics() throws Exception {

		statistic = Statistic.builder()
				.sum(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.avg(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.max(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.min(BigDecimalConstant.getDefaultBigDecimal()
						.toString())
				.count(0)
				.build();

		Mockito.when(statisticsService.getStatistics())
				.thenReturn(statistic);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(STATISTICS_URI)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		String json = jacksonJsonBilderForStatistic.write(statistic)
				.getJson();
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().string(json));

	}

}
