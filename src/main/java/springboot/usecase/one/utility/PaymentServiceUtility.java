package springboot.usecase.one.utility;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerTxnsEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;

@Service
public class PaymentServiceUtility {
	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	CustomerTxnsRepository customerTxnsRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BankCatalogRepository bankCatalogRepository;

	@Transactional
	public CustomerTxnsEntity paymentProcessing(CustomerEntity customer, CustomerAccEntity custAcc,
			CustomerAccBeneficiaryEntity custAccBenf, BigDecimal txnAmt, String remark) {
		custAcc.setAccountBalance(custAcc.getAccountBalance().subtract(txnAmt));
		CustomerTxnsEntity txn = new CustomerTxnsEntity();
		txn.setCustomer(customer);
		txn.setCustomerAccount(custAcc);
		txn.setCustomerBeneficiaryAccount(custAccBenf);
		txn.setTrackingId(CommonConstants.TRK + CommonUtility.getDateTime());
		txn.setTxnAmt(txnAmt);
		txn.setTxnRemark(remark);
		txn.setTxnDate(new Date());
		customerAccountRepository.save(custAcc);
		customerTxnsRepository.save(txn);
		return txn;
	}

	@Transactional
	public void addCustomerAccount(CustomerEntity customer) {
		List<BankCatalogEntity> bankList = bankCatalogRepository.findByActive(CommonConstants.A);
		if (!bankList.isEmpty()) {
			int ranChar = (new Random()).nextInt(bankList.size());
			customerRepository.save(customer);
			CustomerAccEntity custAcc = new CustomerAccEntity();
			custAcc.setCustomer(customer);
			BankCatalogEntity bank = bankList.get(ranChar);
			custAcc.setBankCatalog(bank);
			custAcc.setAccountIfsc(bank.getBankName() + "0000" + (111 + (new Random()).nextInt(999)));
			custAcc.setAccountNumber(bank.getBankCode() + "0000" + CommonUtility.getDateTime());
			custAcc.setAccountBalance(new BigDecimal(CommonConstants.DEFAULT_AMT));
			customerAccountRepository.save(custAcc);
		} else {
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_720, StatusMsgConstants.NO_ACTIVE_BANK_AVALABLE);
		}
	}

}
