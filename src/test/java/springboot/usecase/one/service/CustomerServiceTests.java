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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddCustomerRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.LoginCustomerRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;
import springboot.usecase.one.utility.CommonUtility;
import springboot.usecase.one.utility.PaymentServiceUtility;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerService.class)
class CustomerServiceTests {

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	CustomerAccountRepository customerAccountRepository;

	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@MockBean
	PaymentServiceUtility paymentServiceUtility;

	@MockBean
	CustomerTxnsRepository customerTxnsRepository;

	@Autowired
	CustomerService customerService;

	@Test
	void addCustomer() throws Exception {
		AddCustomerRequest addCustomerRequest = new AddCustomerRequest(1l, "ank@abc.com", "123456", "Ankit",
				"999999999", "01/02/1994", "jaunpur");
		Optional<CustomerEntity> obj = Optional.ofNullable(null);
		doReturn(obj).when(customerRepository).findByMobileOrEmail(addCustomerRequest.getMobile(),
				addCustomerRequest.getEmail());
		ResponseEntity<Map<String, Object>> response = customerService.addCustomer(addCustomerRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void addCustomerNegative() throws Exception {
		AddCustomerRequest addCustomerRequest = new AddCustomerRequest(1l, "ank@abc.com", "123456", "Ankit",
				"999999999", "01/02/1994", "jaunpur");
		Optional<CustomerEntity> obj = Optional.of(getCustomer());
		doReturn(obj).when(customerRepository).findByMobileOrEmail(addCustomerRequest.getMobile(),
				addCustomerRequest.getEmail());
		try {
			customerService.addCustomer(addCustomerRequest);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.DUPLICATE_ENTRY_NOT_ALLOWED, e.getMsg());
		}
	}

	@Test
	void loginValidate() throws Exception {
		LoginCustomerRequest addCustomerRequest = new LoginCustomerRequest();
		addCustomerRequest.setEmail("ank@abc.com");
		addCustomerRequest.setPwd("123455");
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findByPwdAndEmail(CommonUtility.getSHA512("123455"),
				addCustomerRequest.getEmail());
		ResponseEntity<Map<String, Object>> response = customerService.loginValidate(addCustomerRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	void loginValidateNegative() throws Exception {
		LoginCustomerRequest addCustomerRequest = new LoginCustomerRequest();
		addCustomerRequest.setEmail("ank@abc.com");
		addCustomerRequest.setPwd("123455");
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findByPwdAndEmail(CommonUtility.getSHA512("123455"),
				addCustomerRequest.getEmail());
		ResponseEntity<Map<String, Object>> response = customerService.loginValidate(addCustomerRequest);
		String msgres = ((CommonResponse) response.getBody().get(CommonConstants.RESPONSE)).getMessage();
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		assertEquals(StatusMsgConstants.LOGIN_FAIL, msgres);

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
