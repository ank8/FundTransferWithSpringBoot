package springboot.usecase.one.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import springboot.usecase.one.entity.CustomerAccBeneficiaryEntity;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AccBeneficiaryResponse;
import springboot.usecase.one.models.AddAccBeneficiaryRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.GetAccBeneficiaryRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccBeneficiaryRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.JwtToken;

@Service
public class CustomerAccBeneficiaryService {
	private static final Logger LOGGER = LogManager.getLogger(CustomerAccBeneficiaryService.class);
	@Autowired
	CustomerAccBeneficiaryRepository customerAccBeneficiaryRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	BankCatalogRepository bankCatalogRepository;
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	private static final ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
			.withIgnorePaths(CommonConstants.BEN_BANK_CTLG).withIgnorePaths(CommonConstants.BEN_BANK_IFSC)
			.withIgnorePaths(CommonConstants.BEN_NAME);

	public ResponseEntity<Map<String, Object>> addAccBenDetails(String token,
			AddAccBeneficiaryRequest addAccountRequest) {
		long custId = JwtToken.validateToken(token);
		if (custId == addAccountRequest.getCustId()) {
			CustomerEntity customer = getCustomer(custId);
			CustomerAccEntity customerAcc = getCustomerAccount(customer, addAccountRequest.getAccId());
			Optional<BankCatalogEntity> bank = getBank(addAccountRequest.getBankId());
			CustomerAccBeneficiaryEntity custAccBen = new CustomerAccBeneficiaryEntity();
			createAcntBenfAcnt(custAccBen, bank, customer, addAccountRequest, customerAcc);
			boolean flag = customerAccBeneficiaryRepository.exists(Example.of(custAccBen, modelMatcher));
			if (!flag) {
				Map<String, Object> map = new HashMap<>();
				map.put(CommonConstants.CUSTOMER_ID, custId);
				map.put(CommonConstants.CUSTOMER_ACC_ID, customerAcc.getId());
				map.put(CommonConstants.ACC_BEN_ID, customerAccBeneficiaryRepository.save(custAccBen).getId());
				map.put(CommonConstants.RESPONSE,
						new CommonResponse(ErrorCode.ERR_CD_726, StatusMsgConstants.BEN_ACC_ADDED_SUCCESS));
				return new ResponseEntity<>(map, HttpStatus.OK);
			} else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(ErrorCode.ERR_CD_727, StatusMsgConstants.BEN_ALREADY_EXIST);
				}
				throw new CustomExceptionHandler(ErrorCode.ERR_CD_727, StatusMsgConstants.BEN_ALREADY_EXIST);
			}
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_728, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_728, StatusMsgConstants.INVALID_TOKEN);
		}
	}

	public ResponseEntity<Map<String, Object>> getAccBenDetails(String token, GetAccBeneficiaryRequest getAccRequest) {
		long custId = JwtToken.validateToken(token);
		if (custId == getAccRequest.getCustId()) {
			CustomerEntity customer = getCustomer(custId);
			CustomerAccEntity customerAcc = getCustomerAccount(customer, getAccRequest.getAccId());
			Map<String, Object> map = new HashMap<>();
			map.put(CommonConstants.CUSTOMER_ID, custId);
			map.put(CommonConstants.CUSTOMER_ACC_ID, customerAcc.getId());
			List<AccBeneficiaryResponse> list = new ArrayList<>();
			if (getAccRequest.getAccBenId() != null) {
				CustomerAccBeneficiaryEntity benfacc = customerAcc.getCustomerBeneficiaryAccount().stream()
						.filter(data -> data.getId().equals(getAccRequest.getAccBenId())).collect(Collectors.toList())
						.get(0);
				if (benfacc != null) {
					createResponse(benfacc, list);
				} else {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(ErrorCode.ERR_CD_729, StatusMsgConstants.BEN_ACC_NOT_FOUND);
					}
					throw new CustomExceptionHandler(ErrorCode.ERR_CD_729, StatusMsgConstants.BEN_ACC_NOT_FOUND);
				}
			} else {
				customerAcc.getCustomerBeneficiaryAccount().stream().forEach(data -> createResponse(data, list));
			}
			map.put(CommonConstants.CUSTOMER_ACC_BEN, list);
			map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_730, CommonConstants.SUCCESS));
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_731, StatusMsgConstants.INVALID_TOKEN);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_731, StatusMsgConstants.INVALID_TOKEN);
		}
	}

	private CustomerEntity getCustomer(long custId) {
		Optional<CustomerEntity> customerOpt = customerRepository.findById(custId);
		if (!customerOpt.isPresent()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_723, StatusMsgConstants.CUSTOMER_NOT_FOUND);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_723, StatusMsgConstants.CUSTOMER_NOT_FOUND);
		} else {
			return customerOpt.get();
		}
	}

	private CustomerAccEntity getCustomerAccount(CustomerEntity customer, long accId) {
		try {
			List<CustomerAccEntity> customerAccList = customer.getCustomerAccount();
			return customerAccList.stream().filter(data -> data.getId() == accId).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error(ErrorCode.ERR_CD_724, StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_724, StatusMsgConstants.CUSTOMER_ACCOUNT_NOT_FOUND);
		}
	}

	private Optional<BankCatalogEntity> getBank(long bankId) {
		Optional<BankCatalogEntity> bank = bankCatalogRepository.findById(bankId);
		if (!bank.isPresent()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_725, StatusMsgConstants.BANK_NOT_FOUND);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_725, StatusMsgConstants.BANK_NOT_FOUND);
		}
		return bank;
	}

	private void createAcntBenfAcnt(CustomerAccBeneficiaryEntity custAccBen, Optional<BankCatalogEntity> bank,
			CustomerEntity customer, AddAccBeneficiaryRequest addAccountRequest, CustomerAccEntity customerAcc) {
		custAccBen.setBenAccountIfsc(addAccountRequest.getBenAccIfsc());
		custAccBen.setBenAccountName(addAccountRequest.getBenAccName());
		custAccBen.setBenAccountNumber(addAccountRequest.getBenAccNumber());
		custAccBen.setBenBankCatalog(bank.isPresent() ? bank.get() : null);
		custAccBen.setCustomer(customer);
		custAccBen.setCustomerAccount(customerAcc);
	}

	private void createResponse(CustomerAccBeneficiaryEntity benfacc, List<AccBeneficiaryResponse> list) {
		AccBeneficiaryResponse response = new AccBeneficiaryResponse();
		BankCatalogEntity bank = benfacc.getBenBankCatalog();
		response.setId(benfacc.getId());
		response.setBankId(bank.getId());
		response.setBankCd(bank.getBankCode());
		response.setBankName(bank.getBankName());
		response.setBenAccIfsc(benfacc.getBenAccountIfsc());
		response.setBenAccName(benfacc.getBenAccountName());
		response.setBenAccNumber(benfacc.getBenAccountNumber());
		list.add(response);
	}
}
