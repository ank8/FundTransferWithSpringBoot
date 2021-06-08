package springboot.usecase.one.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerTxnsEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.GetPaymentTxnRequest;
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;
import springboot.usecase.one.utility.JwtToken;
import springboot.usecase.one.utility.PaymentServiceUtility;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerTxnsService.class)
class CustomerTxnServiceTests {

	@MockBean
	CustomerAccountRepository customerAccountRepository;

	@MockBean
	CustomerTxnsRepository customerTxnsRepository;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	BankCatalogRepository bankCatalogRepository;

	@MockBean
	PaymentServiceUtility commonPaymentUtility;

	@Autowired
	CustomerTxnsService customerTxnsService;

	@Test
	void payCustomer() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.TEN);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		ResponseEntity<Map<String, Object>> response = customerTxnsService.payCustomer(token, pmtReq);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void payCustomerNegative() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.TEN);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(3l);
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_NOT_FOUND, e.getMsg());
		}

	}

	@Test
	void payCustomerNegative1() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.TEN);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(1, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(3l);
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
		}

	}

	@Test
	void payCustomerNegative2() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.TEN);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();

		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		customer.setCustomerAccount(new ArrayList<>());
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND, e.getMsg());
		}

	}

	@Test
	void payCustomerNegative3() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.TEN);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();

		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.TEN);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		customer.getCustomerAccount().get(0).setCustomerBeneficiaryAccount(new ArrayList<>());
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.BEN_ACC_NOT_FOUND, e.getMsg());
		}

	}

	@Test
	void payCustomerNegative4() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(BigDecimal.ZERO);
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(BigDecimal.ZERO);
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.AMT_SHOULD_GREATER_THHEN_ZERO, e.getMsg());
		}

	}

	@Test
	void payCustomerNegative5() {
		PaymentTxnRequest pmtReq = new PaymentTxnRequest();
		pmtReq.setAmt(new BigDecimal(200000));
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		pmtReq.setRemark("test");
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setId(1l);
		txn.setTrackingId("Trk5236218321");
		txn.setTxnAmt(new BigDecimal(200000));
		txn.setTxnDate(new Date());
		txn.setTxnRemark("test");
		doReturn(txn).when(commonPaymentUtility).paymentProcessing(customer, customer.getCustomerAccount().get(0),
				customer.getCustomerAccount().get(0).getCustomerBeneficiaryAccount().get(0), pmtReq.getAmt(),
				pmtReq.getRemark());
		try {
			customerTxnsService.payCustomer(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INSUFFICIENT_BAL, e.getMsg());
		}

	}

	@Test
	void getTransaction() {
		GetPaymentTxnRequest pmtReq = new GetPaymentTxnRequest();
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);

		ResponseEntity<Map<String, Object>> response = customerTxnsService.getTransaction(token, pmtReq);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void getTransaction1() {
		GetPaymentTxnRequest pmtReq = new GetPaymentTxnRequest();
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		String token = JwtToken.createToken(3, 10);
		CustomerEntity customer = getCustomer();
		Optional<CustomerEntity> obj = Optional.of(customer);
		doReturn(obj).when(customerRepository).findById(3l);
		ResponseEntity<Map<String, Object>> response = customerTxnsService.getTransaction(token, pmtReq);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

	}

	@Test
	void getTransactionNegative() {
		GetPaymentTxnRequest pmtReq = new GetPaymentTxnRequest();
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		String token = JwtToken.createToken(3, 10);
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(3l);
		try {
			customerTxnsService.getTransaction(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.CUSTOMER_NOT_FOUND, e.getMsg());
		}

	}

	@Test
	void getTransactionNegative1() {
		GetPaymentTxnRequest pmtReq = new GetPaymentTxnRequest();
		pmtReq.setCustAccBenId(1l);
		pmtReq.setCustAccId(1l);
		pmtReq.setCustId(3l);
		String token = JwtToken.createToken(1, 10);
		Optional<CustomerEntity> obj = Optional.empty();
		doReturn(obj).when(customerRepository).findById(3l);
		try {
			customerTxnsService.getTransaction(token, pmtReq);
		} catch (CustomExceptionHandler e) {
			assertEquals(StatusMsgConstants.INVALID_TOKEN, e.getMsg());
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
		cAcc.setCustomerTxnsEntity(txnlist);
		custAccBen.setCustomerTxnsEntity(txnlist);
		List<CustomerAccBeneficiaryEntity> list = new ArrayList<CustomerAccBeneficiaryEntity>();
		list.add(custAccBen);
		cAcc.setCustomerBeneficiaryAccount(list);
		custAcnts.add(cAcc);
		customer.setCustomerAccount(custAcnts);
		return customer;
	}

}
