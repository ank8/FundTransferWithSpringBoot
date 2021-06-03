package springboot.usecase.one.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import springboot.usecase.one.models.PaymentTxnModel;
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.service.CustomerTxnsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerTxnsController.class)
class CustomerTxnsControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerTxnsService customerTxnsService;

	String mockContent="{\"amt\": 10,\"mob\": \"9978796756\",\"token\": \"token\"}";
	
	String expected="{\"totalTxns\":1,\"txns\":[{\"payerbank\": \"ICICI\",  \"payerName\": \"Abc def\",  \"benefeciaryName\": \"Anki Sin\", \"benefeciarybank\": \"SBI\",  \"benefeciaryMob\": \"9982828282\",  \"trackingId\": \"TRK-1212121\",  \"txnAmt\": \"10\",\"status\":\"Debit\"}]}";
	
	@Test
	void payCustomer() throws Exception {
		Mockito.when(customerTxnsService.payCustomer(Mockito.any(PaymentTxnRequest.class)))
				.thenReturn(StatusMsgConstants.TXN_SUCCESS);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_TXN_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(StatusMsgConstants.TXN_SUCCESS, response.getContentAsString());
	}
	
	@Test
	void getTransaction() throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<PaymentTxnModel> txnList = new ArrayList<>();
		PaymentTxnModel txnModel= new PaymentTxnModel();
		txnModel.setPayerbank("ICICI");
		txnModel.setPayerName("Abc def");
		txnModel.setBenefeciaryName("Anki Sin");
		txnModel.setBenefeciarybank("SBI");
		txnModel.setBenefeciaryMob("9982828282");
		txnModel.setTrackingId("TRK-1212121");
		txnModel.setTxnAmt("10");
		txnModel.setStatus(CommonConstants.DEBIT);
		txnList.add(txnModel);
		map.put(CommonConstants.TOTAL_TXN, txnList.size());
		map.put(CommonConstants.TXN, txnList);
		ResponseEntity<Map<String, Object>> mockResponse = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(customerTxnsService.getTransaction(Mockito.anyString())).thenReturn(mockResponse);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_TXN_PATH + "/token");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
