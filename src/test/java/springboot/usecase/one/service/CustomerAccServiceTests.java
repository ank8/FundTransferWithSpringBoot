package springboot.usecase.one.service;

import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
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

	@Mock
	CustomerAccService customerAccountService;

	@Test
	void getCustomerAcountDetails() {
		String token = JwtToken.createToken(4l, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(4l);
		customerAccountService.getCustomerAcountDetails(token, 4l);
	}

	CustomerEntity getCustomer() {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(4l);
		customer.setAddress("Jaunpur");
		List<CustomerAccEntity> custAcnts = new ArrayList<CustomerAccEntity>();
		CustomerAccEntity cAcc = new CustomerAccEntity();
		cAcc.setBankCatalog(null);
		cAcc.setAccountBalance(new BigDecimal(2000));
		cAcc.setAccountIfsc("ICICI0000345");
		cAcc.setAccountNumber("345678999997766");
//		cAcc.setCustomerBeneficiaryAccount(null);
//		cAcc.setCustomerBeneficiaryAccount(null);
		custAcnts.add(cAcc);
//		customer.setCustomerAccount(custAcnts);
		return customer;
	}

}
