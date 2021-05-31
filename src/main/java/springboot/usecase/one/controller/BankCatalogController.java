package springboot.usecase.one.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.entity.BankCatalog;
import springboot.usecase.one.models.BankCatalogModel;
import springboot.usecase.one.service.BankCatalogService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class BankCatalogController {

	@Autowired
	BankCatalogService bankCatalogService;

	@PostMapping(CommonConstants.BANK_PATH)
	public BankCatalog addBanks(@Valid @RequestBody BankCatalogModel bankMdl) {
		return bankCatalogService.addBanks(bankMdl);
	}

	@GetMapping(CommonConstants.BANK_PATH)
	public List<BankCatalog> getAllBank() {
		return bankCatalogService.getBanks();
	}

	@PutMapping(CommonConstants.BANK_PATH)
	public BankCatalog updateBankStatus(@Valid @RequestBody BankCatalogModel bank) {
		return bankCatalogService.updateBanks(bank);
	}

	@DeleteMapping(CommonConstants.BANK_PATH + CommonConstants.DELETE_PATH)
	public String deleteBank(@PathVariable Long id) {
		return bankCatalogService.deleteBank(id);
	}

}
