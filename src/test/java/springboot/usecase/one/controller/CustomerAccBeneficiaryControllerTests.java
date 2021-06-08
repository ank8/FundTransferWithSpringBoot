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
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddAccBeneficiaryRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.GetAccBeneficiaryRequest;
import springboot.usecase.one.service.CustomerAccBeneficiaryService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerAccBeneficiaryController.class)
class CustomerAccBeneficiaryControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerAccBeneficiaryService customerAccBeneficiaryService;

	String mockContent = "{\r\n" + "  \"accId\": 1,\r\n" + "  \"bankId\": 2,\r\n"
			+ "  \"benAccIfsc\": \"icic199191\",\r\n" + "  \"benAccName\": \"icici\",\r\n"
			+ "  \"benAccNumber\": \"999999999999999\",\r\n" + "  \"custId\": 1\r\n" + "}";

	String mock = "{\r\n" + "  \"accBenId\": 0,\r\n" + "  \"accId\": 0,\r\n" + "  \"custId\": 0\r\n" + "}";
	String expected = "{\"response\":{\"code\":\"726\",\"message\":\"Beneficiary Account Added Successfully\"},\"custId\":1,\"custAccId\":2,\"accBenId\":3}";

	String res = "{\"accountInfo\":[],\"response\":{\"code\":\"714\",\"message\":\"success\"},\"custId\":1}";
	String expNeg1 = "{\"response\":{\"code\":\"727\",\"message\":\"Beneficiary Account Already Exist For This Customer\"}}";
	String expNeg2 = "{\"response\":{\"code\":\"729\",\"message\":\"Benificiary Account Not Found\"}}";

	@Test
	void addAccBenDetails() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.CUSTOMER_ID, 1);
		map.put(CommonConstants.CUSTOMER_ACC_ID, 2);
		map.put(CommonConstants.ACC_BEN_ID, 3);
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_726, StatusMsgConstants.BEN_ACC_ADDED_SUCCESS));
		Mockito.when(customerAccBeneficiaryService.addAccBenDetails(Mockito.anyString(),
				Mockito.any(AddAccBeneficiaryRequest.class))).thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_BEN_PATH)
				.accept(MediaType.APPLICATION_JSON).content(mockContent).contentType(MediaType.APPLICATION_JSON)
				.header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(expected, response.getContentAsString());
	}

	@Test
	void addAccBenDetailsNegative() throws Exception {
		Mockito.when(customerAccBeneficiaryService.addAccBenDetails(Mockito.anyString(),
				Mockito.any(AddAccBeneficiaryRequest.class)))
				.thenThrow(new CustomExceptionHandler(ErrorCode.ERR_CD_727, StatusMsgConstants.BEN_ALREADY_EXIST));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_BEN_PATH)
				.accept(MediaType.APPLICATION_JSON).content(mockContent).contentType(MediaType.APPLICATION_JSON)
				.header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals(expNeg1, response.getContentAsString());
	}

	@Test
	void getAccBenDetails() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.CUSTOMER_ID, 1l);
		map.put(CommonConstants.ACC_INFO, new ArrayList<>());
		map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_714, CommonConstants.SUCCESS));
		Mockito.when(customerAccBeneficiaryService.getAccBenDetails(Mockito.anyString(),
				Mockito.any(GetAccBeneficiaryRequest.class))).thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_BEN_PATH + CommonConstants.ACC)
				.accept(MediaType.APPLICATION_JSON).content(mock).contentType(MediaType.APPLICATION_JSON)
				.header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(res, result.getResponse().getContentAsString(), false);
	}

	@Test
	void getAccBenDetailsNegative() throws Exception {
		Mockito.when(customerAccBeneficiaryService.getAccBenDetails(Mockito.anyString(),
				Mockito.any(GetAccBeneficiaryRequest.class)))
				.thenThrow(new CustomExceptionHandler(ErrorCode.ERR_CD_729, StatusMsgConstants.BEN_ACC_NOT_FOUND));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_ACNT_BEN_PATH + CommonConstants.ACC)
				.accept(MediaType.APPLICATION_JSON).content(mock).contentType(MediaType.APPLICATION_JSON)
				.header("token", "token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		assertEquals(expNeg2, response.getContentAsString());
	}
}
