package springboot.usecase.one.service;

import java.util.HashMap;
import java.util.Map;

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
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddBankCatalogRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.repository.BankCatalogRepository;

@Service
public class BankCatalogService {
	private static final Logger LOGGER = LogManager.getLogger(BankCatalogService.class);
	@Autowired
	BankCatalogRepository bankCatalogRepository;

	public ResponseEntity<Map<String, Object>> addBanks(AddBankCatalogRequest bankMdl) {
		BankCatalogEntity bank = modelToBank(bankMdl);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ID)
				.withIgnorePaths(CommonConstants.ACTIVE);
		boolean flag = bankCatalogRepository.exists(Example.of(bank, modelMatcher));
		if (flag) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_701, StatusMsgConstants.BANK_ALREADY_EXIST);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_701, StatusMsgConstants.BANK_ALREADY_EXIST);
		}
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.BANK, bankCatalogRepository.save(bank));
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_702, StatusMsgConstants.BANK_ADDED_SUCCESS));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	public ResponseEntity<Map<String, Object>> getBanks() {
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.BANK, bankCatalogRepository.findAll());
		map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_703, CommonConstants.SUCCESS));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	public ResponseEntity<Map<String, Object>> updateBanks(AddBankCatalogRequest bankMdl) {
		BankCatalogEntity bank = modelToBank(bankMdl);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths(CommonConstants.ACTIVE);
		boolean flag = bankCatalogRepository.exists(Example.of(bank, modelMatcher));
		if (flag) {
			Map<String, Object> map = new HashMap<>();
			map.put(CommonConstants.BANK, bankCatalogRepository.save(bank));
			map.put(CommonConstants.RESPONSE,
					new CommonResponse(ErrorCode.ERR_CD_704, StatusMsgConstants.BANK_UPDATED_SUCCESS));
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(ErrorCode.ERR_CD_705, StatusMsgConstants.BANK_NOT_EXIST);
		}
		throw new CustomExceptionHandler(ErrorCode.ERR_CD_705, StatusMsgConstants.BANK_NOT_EXIST);
	}

	public ResponseEntity<Map<String, Object>> deleteBank(Long id) {
		Map<String, Object> map = new HashMap<>();
		if (bankCatalogRepository.existsById(id)) {
			bankCatalogRepository.deleteById(id);
			map.put(CommonConstants.RESPONSE,
					new CommonResponse(ErrorCode.ERR_CD_706, StatusMsgConstants.DELETE_SUCCESS));
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_707, StatusMsgConstants.DELETE_FAILED);
			}
			map.put(CommonConstants.RESPONSE,
					new CommonResponse(ErrorCode.ERR_CD_707, StatusMsgConstants.DELETE_FAILED));
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	private BankCatalogEntity modelToBank(AddBankCatalogRequest bankMdl) {
		return new BankCatalogEntity(bankMdl.getId(), bankMdl.getBankName(), bankMdl.getBankCode(),
				bankMdl.getActive());
	}
}
