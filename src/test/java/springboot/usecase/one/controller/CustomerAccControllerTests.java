package springboot.usecase.one.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.service.CustomerAccService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerAccController.class)
class CustomerAccControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerAccService customerAccountService;


	String mockContent = "{\"accountNumber\":\"99999999999\",\"accountIfsc\":\"ICICI0000231\",\"bankCode\":125,\"token\":\"token\"}";

	String expected = "{\"accountInfo\":[],\"response\":{\"code\":\"714\",\"message\":\"success\"},\"custId\":1}";

	String res="{\"response\":{\"code\":\"709\",\"message\":\"Bank Account Added Successfully\"}}";
	@Test
	void addCustomerAccount() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_709, StatusMsgConstants.BANK_ACC_ADDED_SUCCESS));
		Mockito.when(
				customerAccountService.addCustomerAccount(Mockito.anyString(), Mockito.any(AddAccountRequest.class)))
				.thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON).header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(res, response.getContentAsString());
	}

	@Test
	void getAccountDetails() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.CUSTOMER_ID, 1l);
		map.put(CommonConstants.ACC_INFO, new ArrayList<>());
		map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_714, CommonConstants.SUCCESS));
		Mockito.when(customerAccountService.getCustomerAcountDetails(Mockito.anyString(), Mockito.anyLong()))
				.thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_PATH + "/1").header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
}
