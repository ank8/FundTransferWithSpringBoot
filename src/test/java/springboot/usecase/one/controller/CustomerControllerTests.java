package springboot.usecase.one.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
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
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.models.AddCustomerRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.LoginCustomerRequest;
import springboot.usecase.one.service.CustomerService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class)
class CustomerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerService customerService;

	String mockContent = "{  \"address\": \"Lucknow\",  \"dob\": \"01/03/2000\",  \"email\": \"ankit@abc.com\", \"id\": 1,  \"mobile\": \"9978796756\",  \"name\": \"Abc def\",  \"pwd\": \"ajsasj\"}";

	String regExp = "{\"response\":{\"code\":\"717\",\"message\":\"Successfully Registered\"}}";

	String loginRespStr = "{\"accountInfo\":[{\"id\":null,\"bankCatalog\":{\"id\":1,\"bankName\":\"icici\",\"bankCode\":123,\"active\":\"A\"},\"customer\":null,\"accountNumber\":\"345678999997766\",\"accountIfsc\":\"ICICI0000345\",\"accountBalance\":2000,\"customerBeneficiaryAccount\":null,\"customerTxnsEntity\":null}]}";

	@Test
	void customerRegister() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_717, StatusMsgConstants.REGISTER_SUCCESS));
		Mockito.when(customerService.addCustomer(Mockito.any(AddCustomerRequest.class)))
				.thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(regExp, response.getContentAsString());
	}

	@Test
	void loginValidation() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.ACC_INFO, getCustomer().getCustomerAccount());
		ResponseEntity<Map<String, Object>> loginResp = new ResponseEntity<>(map, HttpStatus.OK);
		Mockito.when(customerService.loginValidate(Mockito.any(LoginCustomerRequest.class))).thenReturn(loginResp);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.CUSTOMER_LOGIN_PATH)
				.accept(MediaType.APPLICATION_JSON).content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		JSONAssert.assertEquals(loginRespStr, result.getResponse().getContentAsString(), false);

	}

	CustomerEntity getCustomer() {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(4l);
		customer.setAddress("Jaunpur");
		List<CustomerAccEntity> custAcnts = new ArrayList<CustomerAccEntity>();
		CustomerAccEntity cAcc = new CustomerAccEntity();
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		cAcc.setBankCatalog(bank);
		cAcc.setAccountBalance(new BigDecimal(2000));
		cAcc.setAccountIfsc("ICICI0000345");
		cAcc.setAccountNumber("345678999997766");
		custAcnts.add(cAcc);
		customer.setCustomerAccount(custAcnts);
		return customer;
	}

}
