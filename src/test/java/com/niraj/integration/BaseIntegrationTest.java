package com.niraj.integration;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.niraj.Application;
import com.niraj.TransactionObjectJson;
import com.niraj.entites.Statistic;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public abstract class BaseIntegrationTest {
	
	protected MockMvc mockMvc;

	protected static final String NO_CONTENT = "";

	protected static final String TRANSACTION_URI = "/transactions";
	protected static final String STATISTICS_URI ="/statistics";

	protected JacksonTester<TransactionObjectJson> jacksonJsonBilderForTransaction;
	
	protected JacksonTester<Statistic> jacksonJsonBilderForStatistics;
	

	@Autowired
	private GenericWebApplicationContext webApplicationContext;

	@Before
	public void getContext() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		assertNotNull(mockMvc);
		ObjectMapper objectMapper = new ObjectMapper();
		JacksonTester.initFields(this, objectMapper);
	}
	
	//@Test
	public void test()
	{
		
	}


}
