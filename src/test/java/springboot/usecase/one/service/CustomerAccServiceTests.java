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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.JwtToken;

@ExtendWith(SpringExtension.class)
@SpringBootTest
//@WebMvcTest(value = CustomerAccService.class)
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
		ResponseEntity<Map<String, Object>> response=customerAccountService.getCustomerAcountDetails(token, 4l);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		
	}

	CustomerEntity getCustomer() {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(4l);
		customer.setAddress("Jaunpur");
		List<CustomerAccEntity> custAcnts = new ArrayList<CustomerAccEntity>();
		CustomerAccEntity cAcc = new CustomerAccEntity();
		BankCatalogEntity bank= new BankCatalogEntity(1l, "icici", 123l, "A");
		cAcc.setBankCatalog(bank);
		cAcc.setAccountBalance(new BigDecimal(2000));
		cAcc.setAccountIfsc("ICICI0000345");
		cAcc.setAccountNumber("345678999997766");
		custAcnts.add(cAcc);
		customer.setCustomerAccount(custAcnts);
		return customer;
	}

}
