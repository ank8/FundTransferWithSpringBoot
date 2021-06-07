package springboot.usecase.one.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.models.AddAccBeneficiaryRequest;
import springboot.usecase.one.models.GetAccBeneficiaryRequest;
import springboot.usecase.one.service.CustomerAccBeneficiaryService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class CustomerAccBeneficiaryController {

	@Autowired
	CustomerAccBeneficiaryService customerAccBeneficiaryService;

	@PostMapping(CommonConstants.CUSTOMER_ACNT_BEN_PATH)
	public ResponseEntity<Map<String, Object>> addAccBenDetails(@RequestHeader(CommonConstants.TOKEN) String token,
			@Valid @RequestBody AddAccBeneficiaryRequest addAccountRequest) {
		return customerAccBeneficiaryService.addAccBenDetails(token, addAccountRequest);
	}

	@PostMapping(CommonConstants.CUSTOMER_ACNT_BEN_PATH + CommonConstants.ACC)
	public ResponseEntity<Map<String, Object>> getAccBenDetails(@RequestHeader(CommonConstants.TOKEN) String token,
			@Valid @RequestBody GetAccBeneficiaryRequest getAccRequest) {
		return customerAccBeneficiaryService.getAccBenDetails(token, getAccRequest);
	}

}
