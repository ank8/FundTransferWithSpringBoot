package springboot.usecase.one.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalog;
import springboot.usecase.one.entity.CustomerAccount;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.utility.JwtToken;

@Service
public class CustomerAccountService {

	@Autowired
	CustomerAccountRepository customerAccountRepository;
	@Autowired
	BankCatalogRepository bankCatalogRepository;

	public String addCustomerAccount(AddAccountRequest addAccountRequest) {
		String token = addAccountRequest.getToken();
		long custId = JwtToken.validateToken(token);
		if (custId != 0) {
			CustomerAccount acnt = new CustomerAccount();
			acnt.setCustId(custId);
			acnt.setAccountNumber(addAccountRequest.getAccountNumber());
			acnt.setAccountIfsc(addAccountRequest.getAccountIfsc());
			Optional<BankCatalog> bankDtl = bankCatalogRepository.findByBankCode(addAccountRequest.getBankCode());
			if (bankDtl.isPresent()) {
				if (bankDtl.get().getActive().equals("A")) {
					ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths("id")
							.withIgnorePaths("accountBalance");
					boolean flag = customerAccountRepository.exists(Example.of(acnt, modelMatcher));
					if (flag) {
						throw new CustomExceptionHandler(StatusMsgConstants.RECORD_ALREADY_EXIST);
					} else {
						acnt.setBankCode(bankDtl.get().getBankCode());
						acnt.setAccountBalance(new BigDecimal(CommonConstants.DEFAULT_AMT));
						customerAccountRepository.save(acnt);
					}
				} else {
					throw new CustomExceptionHandler(StatusMsgConstants.BANK_INACTIVE);
				}
			} else {
				throw new CustomExceptionHandler(StatusMsgConstants.BANK_NOT_EXIST);
			}

		} else {
			throw new CustomExceptionHandler(StatusMsgConstants.INVALID_TOKEN);
		}
		return StatusMsgConstants.ACC_ADD_SUCCESS;

	}

	public ResponseEntity<Map<String, Object>> getCustomerAcountDetails(String token) {
		long custId = JwtToken.validateToken(token);
		if (custId != 0) {
			CustomerAccount custAcnt = customerAccountRepository.findByCustId(custId);
			Map<String, Object> map = new HashMap<>();
			map.put("data", custAcnt);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			throw new CustomExceptionHandler(StatusMsgConstants.INVALID_TOKEN);
		}
	}

}
