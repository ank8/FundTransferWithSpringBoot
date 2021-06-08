package springboot.usecase.one.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddBankCatalogRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.repository.BankCatalogRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BankCatalogService.class)
class BankCatalogServiceTests {
	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@Autowired
	BankCatalogService bankCatalogService;

	@Test
	void addBanks() {
		AddBankCatalogRequest bankRequest = new AddBankCatalogRequest(1l, "icici", 123l, "A");
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACTIVE);
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		doReturn(false).when(bankCatalogRepository).exists(Example.of(bank, modelMatcher));
		doReturn(bank).when(bankCatalogRepository).save(bank);
		ResponseEntity<Map<String, Object>> response = bankCatalogService.addBanks(bankRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		String msgres = ((CommonResponse) response.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(StatusMsgConstants.BANK_ADDED_SUCCESS, msgres);
	}

	@Test
	void getBanks() {
		List<BankCatalogEntity> list = new ArrayList<BankCatalogEntity>();
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		list.add(bank);
		doReturn(list).when(bankCatalogRepository).findAll();
		ResponseEntity<Map<String, Object>> returnres = bankCatalogService.getBanks();
		String msgres = ((CommonResponse) returnres.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(HttpStatus.OK.value(), returnres.getStatusCodeValue());
		assertEquals(CommonConstants.SUCCESS, msgres);
	}

	@Test
	void updateBanks() {
		AddBankCatalogRequest bankRequest = new AddBankCatalogRequest(1l, "icici", 123l, "A");
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ACTIVE);
		doReturn(bank).when(bankCatalogRepository).save(bank);
		doReturn(true).when(bankCatalogRepository).exists(Example.of(bank, modelMatcher));
		ResponseEntity<Map<String, Object>> response = null;
		try {
			response = bankCatalogService.updateBanks(bankRequest);
			assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
			String msgres = ((CommonResponse) response.getBody().get(CommonConstants.RESPONSE)).getMessage();
			assertEquals(StatusMsgConstants.BANK_NOT_EXIST, msgres);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.BANK_NOT_EXIST, e.getMsg());
		}
	}

	@Test
	void deleteBank() {
		doReturn(true).when(bankCatalogRepository).existsById(1l);
		ResponseEntity<Map<String, Object>> returnres = bankCatalogService.deleteBank(1l);
		String msgres = ((CommonResponse) returnres.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(HttpStatus.OK.value(), returnres.getStatusCodeValue());
		assertEquals(StatusMsgConstants.DELETE_SUCCESS, msgres);
	}

	@Test
	void deleteBankNegative() {
		doReturn(false).when(bankCatalogRepository).existsById(1l);
		ResponseEntity<Map<String, Object>> returnres = bankCatalogService.deleteBank(1l);
		String msgres = ((CommonResponse) returnres.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(HttpStatus.OK.value(), returnres.getStatusCodeValue());
		assertEquals(StatusMsgConstants.DELETE_FAILED, msgres);
	}
}
