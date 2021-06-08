package springboot.usecase.one.utiliy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerTxnsEntity;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;
import springboot.usecase.one.utility.PaymentServiceUtility;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PaymentServiceUtility.class)
class PaymentServiceUtilityTests {

	@MockBean
	CustomerAccountRepository customerAccountRepository;

	@MockBean
	CustomerTxnsRepository customerTxnsRepository;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@Autowired
	PaymentServiceUtility paymentServiceUtility;

	@Test
	void paymentProcessing() throws NoSuchAlgorithmException {
		CustomerEntity customer = getCustomer();
		doReturn(null).when(customerAccountRepository).save(customer.getCustomerAccount().get(0));
		doReturn(null).when(customerTxnsRepository).save(customer.getCustomerAccount().get(0)
				.getCustomerBeneficiaryAccount().get(0).getCustomerTxnsEntity().get(0));
		CustomerTxnsEntity response = paymentServiceUtility.paymentProcessing(customer,
				customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), BigDecimal.TEN, "test");
		assertNotNull(response);
	}

	@Test
	void addCustomerAccount() {
		List<BankCatalogEntity> bankList = new ArrayList<BankCatalogEntity>();
		bankList.add(new BankCatalogEntity(1l, "icici", 123l, "A"));
		doReturn(bankList).when(bankCatalogRepository).findByActive(CommonConstants.A);
		doReturn(null).when(customerRepository).save(getCustomer());
		doReturn(null).when(customerAccountRepository).save(getCustomer().getCustomerAccount().get(0));
		paymentServiceUtility.addCustomerAccount(getCustomer());
		try {
			paymentServiceUtility.addCustomerAccount(getCustomer());
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	CustomerEntity getCustomer() {
		CustomerEntity customer = new CustomerEntity();
		customer.setId(3l);
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
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		txn.setCustomer(customer);
		txn.setCustomerAccount(cAcc);
		txn.setCustomerBeneficiaryAccount(custAccBen);
		List<CustomerTxnsEntity> txnlist = new ArrayList<CustomerTxnsEntity>();
		txnlist.add(txn);
		custAccBen.setCustomerTxnsEntity(txnlist);
		List<CustomerAccBeneficiaryEntity> list = new ArrayList<CustomerAccBeneficiaryEntity>();
		list.add(custAccBen);
		cAcc.setCustomerBeneficiaryAccount(list);
		custAcnts.add(cAcc);
		customer.setCustomerAccount(custAcnts);
		return customer;
	}
}
