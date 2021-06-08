package springboot.usecase.one.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.JwtToken;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerAccService.class)
class CustomerAccServiceTests {

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	CustomerAccountRepository customerAccountRepository;

	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@Autowired
	CustomerAccService customerAccountService;

	@Test
	void getCustomerAcountDetails() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		ResponseEntity<Map<String, Object>> response = customerAccountService.getCustomerAcountDetails(token, 4l);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void getCustomerAcountDetails1() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		customer.setCustomerAccount(new ArrayList<>());
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		ResponseEntity<Map<String, Object>> response = customerAccountService.getCustomerAcountDetails(token, 4l);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void getCustomerAcountDetailsNegative() {
		String token = JwtToken.createToken(4l, 10);
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(4l);
		try {
			customerAccountService.getCustomerAcountDetails(token, 4l);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_NOT_FOUND, e.getMsg());
		}
	}

	@Test
	void getCustomerAcountDetailsNegative1() {
		String token = JwtToken.createToken(3l, 10);
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(4l);
		try {
			customerAccountService.getCustomerAcountDetails(token, 4l);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
		}
	}

	@Test
	void addCustomerAccount() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		AddAccountRequest request = new AddAccountRequest();
		request.setCustId(4);
		request.setBankCode(123l);
		request.setAccountIfsc("ICICI0000345");
		request.setAccountNumber("345678999997766");
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		Optional<BankCatalogEntity> bankDtl = Optional.of(bank);
		doReturn(bankDtl).when(bankCatalogRepository).findByBankCode(123l);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
		doReturn(false).when(customerAccountRepository)
				.exists(Example.of(customer.getCustomerAccount().get(0), modelMatcher));
		ResponseEntity<Map<String, Object>> returnres = customerAccountService.addCustomerAccount(token, request);
		String msgres = ((CommonResponse) returnres.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(HttpStatus.OK.value(), returnres.getStatusCodeValue());
		assertEquals(StatusMsgConstants.BANK_ACC_ADDED_SUCCESS, msgres);
	}

	@Test
	void addCustomerAccountNegative() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(4l);
		AddAccountRequest request = new AddAccountRequest();
		request.setCustId(4);
		request.setBankCode(123l);
		request.setAccountIfsc("ICICI0000345");
		request.setAccountNumber("345678999997766");
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		Optional<BankCatalogEntity> bankDtl = Optional.of(bank);
		doReturn(bankDtl).when(bankCatalogRepository).findByBankCode(123l);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
		doReturn(false).when(customerAccountRepository)
				.exists(Example.of(customer.getCustomerAccount().get(0), modelMatcher));

		try {
			customerAccountService.addCustomerAccount(token, request);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.BANK_NOT_EXIST, e.getMsg());
		}
	}

	@Test
	void addCustomerAccountNegative1() {
		String token = JwtToken.createToken(3l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(4l);
		AddAccountRequest request = new AddAccountRequest();
		request.setCustId(4);
		request.setBankCode(123l);
		request.setAccountIfsc("ICICI0000345");
		request.setAccountNumber("345678999997766");
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "A");
		Optional<BankCatalogEntity> bankDtl = Optional.of(bank);
		doReturn(bankDtl).when(bankCatalogRepository).findByBankCode(123l);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
		doReturn(false).when(customerAccountRepository)
				.exists(Example.of(customer.getCustomerAccount().get(0), modelMatcher));

		try {
			customerAccountService.addCustomerAccount(token, request);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
		}
	}

	@Test
	void addCustomerAccountNegative2() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		AddAccountRequest request = new AddAccountRequest();
		request.setCustId(4);
		request.setBankCode(123l);
		request.setAccountIfsc("ICICI0000345");
		request.setAccountNumber("345678999997766");
		BankCatalogEntity bank = new BankCatalogEntity(1l, "icici", 123l, "I");
		Optional<BankCatalogEntity> bankDtl = Optional.of(bank);
		doReturn(bankDtl).when(bankCatalogRepository).findByBankCode(123l);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
		doReturn(false).when(customerAccountRepository)
				.exists(Example.of(customer.getCustomerAccount().get(0), modelMatcher));

		try {
			customerAccountService.addCustomerAccount(token, request);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.BANK_INACTIVE, e.getMsg());
		}
	}

	@Test
	void addCustomerAccountNegative3() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		AddAccountRequest request = new AddAccountRequest();
		request.setCustId(4);
		request.setBankCode(123l);
		request.setAccountIfsc("ICICI0000345");
		request.setAccountNumber("345678999997766");
		Optional<BankCatalogEntity> bankDtl = Optional.empty();
		doReturn(bankDtl).when(bankCatalogRepository).findByBankCode(123l);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
		doReturn(false).when(customerAccountRepository)
				.exists(Example.of(customer.getCustomerAccount().get(0), modelMatcher));

		try {
			customerAccountService.addCustomerAccount(token, request);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.BANK_NOT_EXIST, e.getMsg());
		}
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
