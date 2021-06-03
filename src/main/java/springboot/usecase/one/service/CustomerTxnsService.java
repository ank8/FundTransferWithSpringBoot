package springboot.usecase.one.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.Customer;
import springboot.usecase.one.entity.CustomerAccount;
import springboot.usecase.one.entity.CustomerTxns;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.PaymentTxnModel;
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;
import springboot.usecase.one.utility.PaymentServiceUtility;
import springboot.usecase.one.utility.JwtToken;

@Service
public class CustomerTxnsService {

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	CustomerTxnsRepository customerTxnsRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BankCatalogRepository bankCatalogRepository;

	@Autowired
	PaymentServiceUtility commonPaymentUtility;

	public String payCustomer(PaymentTxnRequest paymentTxnRequest) {
		String token = paymentTxnRequest.getToken();
		long payerId = JwtToken.validateToken(token);
		if (payerId != 0) {
			BigDecimal txnAmt = paymentTxnRequest.getAmt();
			if (txnAmt.compareTo(BigDecimal.ONE) < 0) {
				throw new CustomExceptionHandler("710",StatusMsgConstants.AMT_SHOULD_GREATER_THHEN_ZERO);
			}
			String mobNumber = paymentTxnRequest.getMob();
			CustomerAccount payerAcc = customerAccountRepository.findByCustId(payerId);
			commonPaymentUtility.checkPayerAccDetails(payerAcc);
			Customer benificiary = customerRepository.findByMobile(mobNumber);
			if (benificiary == null) {
				throw new CustomExceptionHandler("711",StatusMsgConstants.BEN_NOT_FOUND);
			}
			CustomerAccount benificiaryAcc = customerAccountRepository.findByCustId(benificiary.getId());
			commonPaymentUtility.checkBenAccDetails(benificiaryAcc);
			if (payerAcc.getAccountBalance().compareTo(txnAmt) < 0) {
				throw new CustomExceptionHandler("712",StatusMsgConstants.INSUFFICIENT_BAL);
			}
			commonPaymentUtility.paymentProcessing(payerAcc, benificiaryAcc, txnAmt);

		}
		return StatusMsgConstants.TXN_SUCCESS;
	}

	public ResponseEntity<Map<String, Object>> getTransaction(String token) {
		Map<String, Object> map = new HashMap<>();
		List<PaymentTxnModel> txnList = new ArrayList<>();
		long payerId = JwtToken.validateToken(token);
		if (payerId != 0) {
			CustomerAccount payerAcc = customerAccountRepository.findByCustId(payerId);
			commonPaymentUtility.checkPayerAccount(payerAcc);
			List<CustomerTxns> custxnList = customerTxnsRepository.findBySenderIdOrReceiverId(payerId, payerId);
			custxnList.stream().forEach(data -> {
				if (data.getReceiverId() == payerId) {
					PaymentTxnModel txnModel = new PaymentTxnModel();
					commonPaymentUtility.setCreditTxns(txnModel, payerAcc, data, payerId);
					txnList.add(txnModel);
				}
				if (data.getSenderId() == payerId) {
					PaymentTxnModel txnModel = new PaymentTxnModel();
					commonPaymentUtility.setDebitTxns(txnModel, payerAcc, data, payerId);
					txnList.add(txnModel);
				}
			});

			map.put(CommonConstants.TOTAL_TXN, txnList.size());
			map.put(CommonConstants.TXN, txnList);

		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
