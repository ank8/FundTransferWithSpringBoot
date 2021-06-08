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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddAccBeneficiaryRequest;
import springboot.usecase.one.models.GetAccBeneficiaryRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccBeneficiaryRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.JwtToken;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerAccBeneficiaryService.class)
class CustomerAccBeneficiaryServiceTests {

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	CustomerAccountRepository customerAccountRepository;

	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@MockBean
	CustomerAccBeneficiaryRepository customerAccBeneficiaryRepository;

	@Autowired
	CustomerAccBeneficiaryService customerAccBeneficiaryService;

	@Test
	void addAccBenDetails() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.of(customer);
		AddAccBeneficiaryRequest addAccountRequest = new AddAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		addAccountRequest.setBankId(1l);
		addAccountRequest.setBenAccIfsc("ICICI0000345");
		addAccountRequest.setBenAccName("Ankit");
		addAccountRequest.setBenAccNumber("345678999997766");
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);
		CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
		custAccBen.setId(1l);
		Mockito.when(customerAccBeneficiaryRepository.save(Mockito.any(CustomerAccBeneficiaryEntity.class)))
				.thenReturn(custAccBen);

		ResponseEntity<Map<String, Object>> response = customerAccBeneficiaryService.addAccBenDetails(token,
				addAccountRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void addAccBenDetailsNegative() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.empty();
		AddAccBeneficiaryRequest addAccountRequest = new AddAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		addAccountRequest.setBankId(1l);
		addAccountRequest.setBenAccIfsc("ICICI0000345");
		addAccountRequest.setBenAccName("Ankit");
		addAccountRequest.setBenAccNumber("345678999997766");
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);
		CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
		custAccBen.setId(1l);
		Mockito.when(customerAccBeneficiaryRepository.save(Mockito.any(CustomerAccBeneficiaryEntity.class)))
				.thenReturn(custAccBen);

		try {
			customerAccBeneficiaryService.addAccBenDetails(token, addAccountRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_NOT_FOUND, e.getMsg());
		}
	}

	@Test
	void addAccBenDetailsNegative2() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();

		AddAccBeneficiaryRequest addAccountRequest = new AddAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		addAccountRequest.setBankId(1l);
		addAccountRequest.setBenAccIfsc("ICICI0000345");
		addAccountRequest.setBenAccName("Ankit");
		addAccountRequest.setBenAccNumber("345678999997766");

		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);
		CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
		custAccBen.setId(1l);
		customer.setCustomerAccount(new ArrayList<>());
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		Mockito.when(customerAccBeneficiaryRepository.save(Mockito.any(CustomerAccBeneficiaryEntity.class)))
				.thenReturn(custAccBen);

		try {
			customerAccBeneficiaryService.addAccBenDetails(token, addAccountRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND, e.getMsg());
		}
	}

	@Test
	void addAccBenDetailsNegative3() {
		String token = JwtToken.createToken(3l, 10);
		CustomerEntity customer = getCustomerData();

		AddAccBeneficiaryRequest addAccountRequest = new AddAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		addAccountRequest.setBankId(1l);
		addAccountRequest.setBenAccIfsc("ICICI0000345");
		addAccountRequest.setBenAccName("Ankit");
		addAccountRequest.setBenAccNumber("345678999997766");

		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);
		CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
		custAccBen.setId(1l);
		customer.setCustomerAccount(new ArrayList<>());
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		Mockito.when(customerAccBeneficiaryRepository.save(Mockito.any(CustomerAccBeneficiaryEntity.class)))
				.thenReturn(custAccBen);

		try {
			customerAccBeneficiaryService.addAccBenDetails(token, addAccountRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
		}
	}

	@Test
	void getAccBenDetails() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.of(customer);
		GetAccBeneficiaryRequest addAccountRequest = new GetAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);
		ResponseEntity<Map<String, Object>> response = customerAccBeneficiaryService.getAccBenDetails(token,
				addAccountRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void getAccBenDetailsNegative() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.empty();
		GetAccBeneficiaryRequest addAccountRequest = new GetAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);

		try {
			customerAccBeneficiaryService.getAccBenDetails(token, addAccountRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_NOT_FOUND, e.getMsg());
		}
	}

	@Test
	void getAccBenDetailsNegative2() {
		String token = JwtToken.createToken(3l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.empty();
		GetAccBeneficiaryRequest addAccountRequest = new GetAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);

		try {
			customerAccBeneficiaryService.getAccBenDetails(token, addAccountRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
		}
	}

	@Test
	void getAccBenDetails1() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomerData();
		Optional<CustomerEntity> obj = Optional.of(customer);
		GetAccBeneficiaryRequest addAccountRequest = new GetAccBeneficiaryRequest();
		addAccountRequest.setCustId(4l);
		addAccountRequest.setAccId(1);
		addAccountRequest.setAccBenId(1l);
		doReturn(obj).when(customerRepository).findById(4l);
		doReturn(Optional.of(customer.getCustomerAccount().get(0).getBankCatalog())).when(bankCatalogRepository)
				.findById(1l);

		ResponseEntity<Map<String, Object>> response = customerAccBeneficiaryService.getAccBenDetails(token,
				addAccountRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	CustomerEntity getCustomerData() {
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
		cAcc.setId(1l);
		CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
		custAccBen.setId(1l);
		custAccBen.setBenAccountIfsc("ICICI0000345");
		custAccBen.setBenAccountName("Ankit");
		custAccBen.setBenAccountNumber("345678999997766");
		custAccBen.setBenBankCatalog(new BankCatalogEntity(1l, "icici", 123l, "A"));
		List<CustomerAccBeneficiaryEntity> list = new ArrayList<CustomerAccBeneficiaryEntity>();
		list.add(custAccBen);
		cAcc.setCustomerBeneficiaryAccount(list);
		custAcnts.add(cAcc);
		customer.setCustomerAccount(custAcnts);
		return customer;
	}

}
