package springboot.usecase.one.controller;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import com.jayway.jsonpath.JsonPath;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.Customer;
import springboot.usecase.one.models.CustomerModel;
import springboot.usecase.one.models.LoginRequest;
import springboot.usecase.one.service.CustomerService;
import springboot.usecase.one.utility.JwtToken;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerService customerService;

	Customer mockCustomer = new Customer(1l, "anki@ch.com", "ajsasj", "Abc def", "9978796756", "01/03/2000", "Lucknow");

	String mockContent = "{  \"address\": \"Lucknow\",  \"dob\": \"01/03/2000\",  \"email\": \"string\", \"id\": 1,  \"mobile\": \"9978796756\",  \"name\": \"Abc def\",  \"pwd\": \"ajsasj\"}";

	@Test
	void customerRegister() throws Exception {
		Mockito.when(customerService.addCustomer(Mockito.any(CustomerModel.class)))
				.thenReturn(StatusMsgConstants.REGISTER_SUCCESS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assert.assertEquals(StatusMsgConstants.REGISTER_SUCCESS, response.getContentAsString());
	}

	@Test
	void loginValidation() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(StatusMsgConstants.MSG, StatusMsgConstants.LOGIN_SUCCESS);
		map.put(StatusMsgConstants.PERSONAL_INFO, mockCustomer);
		map.put(StatusMsgConstants.TOKEN, JwtToken.createToken(mockCustomer.getId(), CommonConstants.JWT_TOKEN_EXP));
		map.put(StatusMsgConstants.ACC_INFO, "");
		ResponseEntity<Map<String, Object>> loginResp = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(customerService.loginValidate(Mockito.any(LoginRequest.class))).thenReturn(loginResp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_LOGIN_PATH)
				.accept(MediaType.APPLICATION_JSON).content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		String strResp = result.getResponse().getContentAsString();
		String msg = JsonPath.parse(strResp).read("$." + StatusMsgConstants.MSG);
		Assert.assertEquals(StatusMsgConstants.LOGIN_SUCCESS, msg);

	}

}
