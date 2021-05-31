package springboot.usecase.one.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.BankCatalog;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.BankCatalogModel;
import springboot.usecase.one.repository.BankCatalogRepository;

@Service
public class BankCatalogService {

	@Autowired
	BankCatalogRepository bankCatalogRepository;

	public BankCatalog addBanks(BankCatalogModel bankMdl) {
		BankCatalog bank = modelToBank(bankMdl);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnorePaths("active");
		boolean flag = bankCatalogRepository.exists(Example.of(bank, modelMatcher));
		if (flag) {
			throw new CustomExceptionHandler(StatusMsgConstants.BANK_ALREADY_EXIST);
		}
		return bankCatalogRepository.save(bank);
	}

	public List<BankCatalog> getBanks() {
		return bankCatalogRepository.findAll();
	}

	public BankCatalog updateBanks(BankCatalogModel bankMdl) {
		BankCatalog bank = modelToBank(bankMdl);
		ExampleMatcher modelMatcher = ExampleMatcher.matching().withIgnorePaths("active");
		boolean flag = bankCatalogRepository.exists(Example.of(bank, modelMatcher));
		if (flag) {
			return bankCatalogRepository.save(bank);
		}
		throw new CustomExceptionHandler(StatusMsgConstants.BANK_NOT_EXIST);
	}

	public String deleteBank(Long id) {
		if (bankCatalogRepository.existsById(id)) {
			bankCatalogRepository.deleteById(id);
			return StatusMsgConstants.DELETE_SUCCESS;
		}
		return StatusMsgConstants.DELETE_FAILED;
	}

	private BankCatalog modelToBank(BankCatalogModel bankMdl) {
		return new BankCatalog(bankMdl.getId(), bankMdl.getBankName(), bankMdl.getBankCode(), bankMdl.getActive());
	}
}
