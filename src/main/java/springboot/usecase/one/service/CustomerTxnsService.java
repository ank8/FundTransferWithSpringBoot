package springboot.usecase.one.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.entity.CustomerTxnsEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.GetPaymentTxnRequest;
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.models.PaymentTxnResponse;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.repository.CustomerTxnsRepository;
import springboot.usecase.one.utility.JwtToken;
import springboot.usecase.one.utility.PaymentServiceUtility;

@Service
public class CustomerTxnsService {
	private static final Logger LOGGER = LogManager.getLogger(CustomerTxnsService.class);

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

	public ResponseEntity<Map<String, Object>> payCustomer(String token, PaymentTxnRequest paymentTxnRequest) {
		Map<String, Object> map = new HashMap<>();
		long custId = JwtToken.validateToken(token);
		if (custId == paymentTxnRequest.getCustId()) {
			BigDecimal txnAmt = paymentTxnRequest.getAmt();
			checkAmountNotZero(txnAmt);
			Optional<CustomerEntity> customerOpt = customerRepository.findById(custId);
			if (customerOpt.isPresent()) {
				CustomerEntity customer = customerOpt.get();
				CustomerAccEntity custAcc = getCustAccount(customer, paymentTxnRequest.getCustAccId());
				CustomerAccBeneficiaryEntity custAccBenf = getCustBeneficiaryAcc(custAcc,
						paymentTxnRequest.getCustAccBenId());
				checkInsufficientBal(custAcc, txnAmt);
				CustomerTxnsEntity txn = commonPaymentUtility.paymentProcessing(customer, custAcc, custAccBenf, txnAmt,
						paymentTxnRequest.getRemark());
				map.put(CommonConstants.CUSTOMER_ID, custId);
				map.put(CommonConstants.CUSTOMER_ACC_ID, custAcc.getId());
				map.put(CommonConstants.ACC_BEN_ID, custAccBenf.getId());
				map.put(CommonConstants.TXN_ID, txn.getId());
				map.put(CommonConstants.TXN_TRCK_ID, txn.getTrackingId());
				map.put(CommonConstants.TXN_AMT, txn.getTxnAmt());
				map.put(CommonConstants.TXN_DAT, txn.getTxnDate());
				map.put(CommonConstants.RESPONSE,
						new CommonResponse(ErrorCode.ERR_CD_736, StatusMsgConstants.TXN_SUCCESS));
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ErrorCode.ERR_CD_737, StatusMsgConstants.CUSTOMER_NOT_FOUND);
				}
				throw new CustomExceptionHandler(ErrorCode.ERR_CD_737, StatusMsgConstants.CUSTOMER_NOT_FOUND);
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_738, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_738, StatusMsgConstants.INVALID_TOKEN);
		}
	}

	public ResponseEntity<Map<String, Object>> getTransaction(String token, GetPaymentTxnRequest getPaymentTxnRequest) {
		Map<String, Object> map = new HashMap<>();
		List<PaymentTxnResponse> txnList = new ArrayList<>();

		long custId = JwtToken.validateToken(token);
		if (custId == getPaymentTxnRequest.getCustId()) {
			Optional<CustomerEntity> customerOpt = customerRepository.findById(custId);
			if (customerOpt.isPresent()) {
				CustomerEntity customer = customerOpt.get();
				CustomerAccEntity custAcc = getCustAccount(customer, getPaymentTxnRequest.getCustAccId());
				List<CustomerTxnsEntity> txns = null;
				if (getPaymentTxnRequest.getCustAccBenId() != 0) {
					CustomerAccBeneficiaryEntity custAccBenf = getCustBeneficiaryAcc(custAcc,
							getPaymentTxnRequest.getCustAccBenId());
					txns = custAccBenf.getCustomerTxnsEntity();
				} else {
					txns = custAcc.getCustomerTxnsEntity();
				}
				txns.stream().forEach(data -> {
					PaymentTxnResponse res = new PaymentTxnResponse();
					res.setPayerName(data.getCustomer().getName());
					res.setPayerbank(data.getCustomerAccount().getBankCatalog().getBankName());
					res.setPayerAcc(data.getCustomerAccount().getAccountNumber());
					res.setBenefeciaryName(data.getCustomerBeneficiaryAccount().getBenAccountName());
					res.setBenefeciarybank(data.getCustomerBeneficiaryAccount().getBenBankCatalog().getBankName());
					res.setBenefeciaryAcc(data.getCustomerBeneficiaryAccount().getBenAccountNumber());
					res.setTrackingId(data.getTrackingId());
					res.setTxnAmt(data.getTxnAmt().toString());
					res.setTxnDate(data.getTxnDate());
					res.setStatus(CommonConstants.DEBIT);
					txnList.add(res);
				});

			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ErrorCode.ERR_CD_741, StatusMsgConstants.CUSTOMER_NOT_FOUND);
				}
				throw new CustomExceptionHandler(ErrorCode.ERR_CD_741, StatusMsgConstants.CUSTOMER_NOT_FOUND);
			}
			map.put(CommonConstants.TOTAL_TXN, txnList.size());
			map.put(CommonConstants.TXN, txnList);

		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_742, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_742, StatusMsgConstants.INVALID_TOKEN);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	private void checkAmountNotZero(BigDecimal txnAmt) {
		if (txnAmt.compareTo(BigDecimal.ZERO) <= 0) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_732, StatusMsgConstants.AMT_SHOULD_GREATER_THHEN_ZERO);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_732, StatusMsgConstants.AMT_SHOULD_GREATER_THHEN_ZERO);
		}
	}

	private CustomerAccEntity getCustAccount(CustomerEntity customer, long acntId) {
		try {
			return customer.getCustomerAccount().stream().filter(data -> data.getId().equals(acntId))
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ErrorCode.ERR_CD_733, StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND, e.getMessage());
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_733, StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND);
		}
	}

	private CustomerAccBeneficiaryEntity getCustBeneficiaryAcc(CustomerAccEntity custAcc, long accBenId) {
		try {
			return custAcc.getCustomerBeneficiaryAccount().stream().filter(data -> data.getId().equals(accBenId))
					.collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ErrorCode.ERR_CD_734, StatusMsgConstants.BEN_ACC_NOT_FOUND, e.getMessage());
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_734, StatusMsgConstants.BEN_ACC_NOT_FOUND);
		}
	}

	private void checkInsufficientBal(CustomerAccEntity custAcc, BigDecimal txnAmt) {
		if (custAcc.getAccountBalance().compareTo(txnAmt) < 0) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_735, StatusMsgConstants.INSUFFICIENT_BAL);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_735, StatusMsgConstants.INSUFFICIENT_BAL);
		}

	}
}
