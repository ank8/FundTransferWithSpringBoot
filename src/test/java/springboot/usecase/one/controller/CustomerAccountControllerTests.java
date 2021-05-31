package springboot.usecase.one.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.service.CustomerAccountService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerAccountController.class)
class CustomerAccountControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerAccountService customerAccountService;

	AddAccountRequest mockAccount = new AddAccountRequest("99999999999", "ICICI0000231", 125l, "token");

	String mockContent = "{\"accountNumber\":\"99999999999\",\"accountIfsc\":\"ICICI0000231\",\"bankCode\":125,\"token\":\"token\"}";

	String expected = "{\"data\":{\"accountNumber\":\"99999999999\",\"accountIfsc\":\"ICICI0000231\",\"bankCode\":125,\"token\":\"token\"}}";

	@Test
	void addCustomerAccount() throws Exception {
		Mockito.when(customerAccountService.addCustomerAccount(Mockito.any(AddAccountRequest.class)))
				.thenReturn(StatusMsgConstants.ACC_ADD_SUCCESS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(StatusMsgConstants.ACC_ADD_SUCCESS, response.getContentAsString());
	}

	@Test
	void getAccountDetails() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("data", mockAccount);
		ResponseEntity<Map<String, Object>> mockResponse = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(customerAccountService.getCustomerAcountDetails(Mockito.anyString())).thenReturn(mockResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_PATH + "/token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
}
