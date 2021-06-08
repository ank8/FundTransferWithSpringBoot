package springboot.usecase.one.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.BankCatalogEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.CustomerAccResponse;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.JwtToken;

@Service
public class CustomerAccService {
	private static final Logger LOGGER = LogManager.getLogger(CustomerAccService.class);
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	@Autowired
	BankCatalogRepository bankCatalogRepository;
	@Autowired
	CustomerRepository customerRepository;

	public ResponseEntity<Map<String, Object>> addCustomerAccount(String token, AddAccountRequest addAccountRequest) {
		long custId = JwtToken.validateToken(token);
		if (custId == addAccountRequest.getCustId()) {
			Optional<CustomerEntity> customerOpt = customerRepository.findById(custId);
			if (customerOpt.isPresent()) {
				CustomerAccEntity acnt = new CustomerAccEntity();
				CustomerEntity customer = customerOpt.get();
				acnt.setCustomer(customer);
				acnt.setAccountNumber(addAccountRequest.getAccountNumber());
				acnt.setAccountIfsc(addAccountRequest.getAccountIfsc());
				Optional<BankCatalogEntity> bankDtl = bankCatalogRepository
						.findByBankCode(addAccountRequest.getBankCode());
				return checkConditionsAndSaveAcnt(bankDtl, acnt);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ErrorCode.ERR_CD_712, StatusMsgConstants.BANK_NOT_EXIST);
				}
				throw new CustomExceptionHandler(ErrorCode.ERR_CD_712, StatusMsgConstants.BANK_NOT_EXIST);
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_713, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_713, StatusMsgConstants.INVALID_TOKEN);
		}

	}

	public ResponseEntity<Map<String, Object>> getCustomerAcountDetails(String token, long customerId) {
		long custId = JwtToken.validateToken(token);
		if (custId == customerId) {
			Optional<CustomerEntity> customerOpt = customerRepository.findById(custId);
			Map<String, Object> map = new HashMap<>();
			map.put(CommonConstants.CUSTOMER_ID, custId);
			if (customerOpt.isPresent()) {
				List<CustomerAccEntity> custAcnts = customerOpt.get().getCustomerAccount();
				List<CustomerAccResponse> listAcc = new ArrayList<>();
				custAcnts.stream().forEach(data -> {
					CustomerAccResponse accmodel = new CustomerAccResponse();
					BeanUtils.copyProperties(data, accmodel);
					accmodel.setBankCode(data.getBankCatalog().getBankCode());
					listAcc.add(accmodel);
				});
				map.put(CommonConstants.ACC_INFO, listAcc);
			} else {
				map.put(CommonConstants.ACC_INFO, new ArrayList<>());
			}
			map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_714, CommonConstants.SUCCESS));
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_715, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_715, StatusMsgConstants.INVALID_TOKEN);
		}
	}

	private ResponseEntity<Map<String, Object>> checkConditionsAndSaveAcnt(Optional<BankCatalogEntity> bankDtl,
			CustomerAccEntity acnt) {
		if (bankDtl.isPresent()) {
			if (bankDtl.get().getActive().equals(CommonConstants.A)) {
				ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
						.withIgnorePaths(CommonConstants.ACCOUNT_BALANCE);
				boolean flag = customerAccountRepository.exists(Example.of(acnt, modelMatcher));
				if (flag) {
					throw new CustomExceptionHandler(ErrorCode.ERR_CD_708, StatusMsgConstants.RECORD_ALREADY_EXIST);
				} else {
					Map<String, Object> map = new HashMap<>();
					acnt.setBankCatalog(bankDtl.get());
					acnt.setAccountBalance(new BigDecimal(CommonConstants.DEFAULT_AMT));
					customerAccountRepository.save(acnt);
					map.put(CommonConstants.RESPONSE,
							new CommonResponse(ErrorCode.ERR_CD_709, StatusMsgConstants.BANK_ACC_ADDED_SUCCESS));
					return new ResponseEntity<>(map, HttpStatus.OK);
				}
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ErrorCode.ERR_CD_710, StatusMsgConstants.BANK_INACTIVE);
				}
				throw new CustomExceptionHandler(ErrorCode.ERR_CD_710, StatusMsgConstants.BANK_INACTIVE);
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_711, StatusMsgConstants.BANK_NOT_EXIST);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_711, StatusMsgConstants.BANK_NOT_EXIST);
		}

	}

}
