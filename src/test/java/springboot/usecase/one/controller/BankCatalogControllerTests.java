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
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddBankCatalogRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.service.BankCatalogService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BankCatalogController.class)
class BankCatalogControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	BankCatalogService bankCatalogService;

	BankCatalogEntity mockBankCtlg = new BankCatalogEntity(1l, "ICICI", 125l, "A");

	String mockContent = "{\"id\":1,\"bankName\":\"ICICI\",\"bankCode\":125,\"active\":\"A\"}";

	String expected = "{\"bank\":[{\"id\":1,\"bankName\":\"ICICI\",\"bankCode\":125,\"active\":\"A\"}],\"response\":{\"code\":\"703\",\"message\":\"success\"}}";

	String errExpected = "{\"response\":{\"code\":\"701\",\"message\":\"Bank Already Exist\"}}";

	String deleteExpect="{\"response\":{\"code\":\"706\",\"message\":\"Successfully Deleted\"}}";
	@Test
	void addBank() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.BANK, mockBankCtlg);
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_702, StatusMsgConstants.BANK_ADDED_SUCCESS));
		Mockito.when(bankCatalogService.addBanks(Mockito.any(AddBankCatalogRequest.class)))
				.thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.BANK_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void addBankNegative() throws Exception {
		CustomExceptionHandler exception = new CustomExceptionHandler(ErrorCode.ERR_CD_701,
				StatusMsgConstants.BANK_ALREADY_EXIST);
		Mockito.when(bankCatalogService.addBanks(Mockito.any(AddBankCatalogRequest.class))).thenThrow(exception);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(CommonConstants.BASE_PATH + CommonConstants.BANK_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		JSONAssert.assertEquals(errExpected, result.getResponse().getContentAsString(), false);
	}

	@Test
	void getAllBank() throws Exception {
		List<BankCatalogEntity> list = new ArrayList<>();
		list.add(mockBankCtlg);
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.BANK, list);
		map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_703, CommonConstants.SUCCESS));
		Mockito.when(bankCatalogService.getBanks()).thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get(CommonConstants.BASE_PATH + CommonConstants.BANK_PATH);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

	}

	@Test
	void updateBankStatus() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.BANK, mockBankCtlg);
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_704, StatusMsgConstants.BANK_UPDATED_SUCCESS));
		Mockito.when(bankCatalogService.updateBanks(Mockito.any(AddBankCatalogRequest.class))).thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(CommonConstants.BASE_PATH + CommonConstants.BANK_PATH).accept(MediaType.APPLICATION_JSON)
				.content(mockContent).contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	void deleteBank() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_706, StatusMsgConstants.DELETE_SUCCESS));
		Mockito.when(bankCatalogService.deleteBank(Mockito.anyLong())).thenReturn(new ResponseEntity<>(map, HttpStatus.OK));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete(CommonConstants.BASE_PATH + CommonConstants.BANK_PATH + "/1");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(deleteExpect, response.getContentAsString());
	}
}
