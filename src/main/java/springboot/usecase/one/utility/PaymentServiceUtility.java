package springboot.usecase.one.utility;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalog;
import springboot.usecase.one.entity.Customer;
import springboot.usecase.one.entity.CustomerAccount;
import springboot.usecase.one.entity.CustomerTxns;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.PaymentTxnModel;
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
	public void paymentProcessing(CustomerAccount payerAcc, CustomerAccount benificiaryAcc, BigDecimal txnAmt) {
		payerAcc.setAccountBalance(payerAcc.getAccountBalance().subtract(txnAmt));
		benificiaryAcc.setAccountBalance(benificiaryAcc.getAccountBalance().add(txnAmt));
		CustomerTxns txn = new CustomerTxns();
		txn.setSenderId(payerAcc.getCustId());
		txn.setReceiverId(benificiaryAcc.getCustId());
		txn.setTrackingId(CommonConstants.TRK + CommonUtility.getDateTime());
		txn.setTxnAmt(txnAmt);
		customerAccountRepository.save(payerAcc);
		customerAccountRepository.save(benificiaryAcc);
		customerTxnsRepository.save(txn);
	}
	
	
	
	public void checkPayerAccDetails(CustomerAccount payerAcc) {
		if (payerAcc == null) {
			throw new CustomExceptionHandler(StatusMsgConstants.PAYER_ACC_NOT_FOUND);
		}
		Optional<BankCatalog> payerBank = bankCatalogRepository.findByBankCode(payerAcc.getBankCode());
		if (payerBank.isPresent()) {
			if (!payerBank.get().getActive().equals("A")) {
				throw new CustomExceptionHandler(StatusMsgConstants.PAYER + " " + StatusMsgConstants.BANK_INACTIVE);
			}
		} else {
			throw new CustomExceptionHandler(StatusMsgConstants.PAYER + " " + StatusMsgConstants.BANK_NOT_EXIST);
		}
	}
	public void checkBenAccDetails(CustomerAccount benificiaryAcc) {
		if (benificiaryAcc == null) {
			throw new CustomExceptionHandler(StatusMsgConstants.BEN_ACC_NOT_FOUND);
		}
		Optional<BankCatalog> benBank = bankCatalogRepository.findByBankCode(benificiaryAcc.getBankCode());
		if (benBank.isPresent()) {
			if (!benBank.get().getActive().equals("A")) {
				throw new CustomExceptionHandler(
						StatusMsgConstants.BENIFICIARY + " " + StatusMsgConstants.BANK_INACTIVE);
			}
		} else {
			throw new CustomExceptionHandler(StatusMsgConstants.BENIFICIARY + " " + StatusMsgConstants.BANK_NOT_EXIST);
		}
	}
	
	public void checkPayerAccount(CustomerAccount payerAcc) {
		if (payerAcc == null) {
			throw new CustomExceptionHandler(StatusMsgConstants.PAYER_ACC_NOT_FOUND);
		}
	}

	public void setCreditTxns(PaymentTxnModel txnModel, CustomerAccount payerAcc, CustomerTxns data, long payerId) {
		Optional<Customer> payer = customerRepository.findById(data.getSenderId());
		if (payer.isPresent()) {
			txnModel.setPayerName(payer.get().getName());
			CustomerAccount payerAccount = customerAccountRepository.findByCustId(payer.get().getId());
			if (payerAccount != null) {
				Optional<BankCatalog> payerBank = bankCatalogRepository.findByBankCode(payerAccount.getBankCode());
				if (payerBank.isPresent()) {
					txnModel.setPayerbank(payerBank.get().getBankName());
				}
			}
		}
		
		Optional<Customer> beneficiary = customerRepository.findById(payerId);
		if (beneficiary.isPresent()) {
			txnModel.setBenefeciaryName(beneficiary.get().getName());
			txnModel.setBenefeciaryMob(beneficiary.get().getMobile());
		}
		Optional<BankCatalog> beneficiaryBank = bankCatalogRepository.findByBankCode(payerAcc.getBankCode());
		if (beneficiaryBank.isPresent()) {
			txnModel.setBenefeciarybank(beneficiaryBank.get().getBankName());
		}

		txnModel.setTrackingId(data.getTrackingId());
		txnModel.setTxnAmt(data.getTxnAmt().toString());
		txnModel.setStatus(CommonConstants.CREDIT);
	}

	public void setDebitTxns(PaymentTxnModel txnModel, CustomerAccount payerAcc, CustomerTxns data, long payerId) {
		Optional<BankCatalog> payerBank = bankCatalogRepository.findByBankCode(payerAcc.getBankCode());
		if (payerBank.isPresent()) {
			txnModel.setPayerbank(payerBank.get().getBankName());
		}
		Optional<Customer> payer = customerRepository.findById(payerId);
		if (payer.isPresent()) {
			txnModel.setPayerName(payer.get().getName());
		}

		Optional<Customer> beneficiary = customerRepository.findById(data.getReceiverId());
		if (beneficiary.isPresent()) {
			txnModel.setBenefeciaryName(beneficiary.get().getName());
			txnModel.setBenefeciaryMob(beneficiary.get().getMobile());
			CustomerAccount beneficiaryAccount = customerAccountRepository.findByCustId(beneficiary.get().getId());
			if (beneficiaryAccount != null) {
				Optional<BankCatalog> beneficiaryBank = bankCatalogRepository
						.findByBankCode(beneficiaryAccount.getBankCode());
				if (beneficiaryBank.isPresent()) {
					txnModel.setBenefeciarybank(beneficiaryBank.get().getBankName());
				}
			}
		}
		txnModel.setTxnAmt(data.getTxnAmt().toString());
		txnModel.setTrackingId(data.getTrackingId());
		txnModel.setStatus(CommonConstants.DEBIT);
	}
}
