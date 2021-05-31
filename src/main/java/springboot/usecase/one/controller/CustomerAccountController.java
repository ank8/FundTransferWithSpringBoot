package springboot.usecase.one.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.models.AddAccountRequest;
import springboot.usecase.one.service.CustomerAccountService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class CustomerAccountController {

	@Autowired
	CustomerAccountService customerAccountService;

	@PostMapping(CommonConstants.CUSTOMER_ACNT_PATH)
	public String addCustomerAccount(@Valid @RequestBody AddAccountRequest addAccountRequest) {
		return customerAccountService.addCustomerAccount(addAccountRequest);
	}

	@GetMapping(CommonConstants.CUSTOMER_ACNT_PATH + CommonConstants.PATH_VARIABLE)
	public ResponseEntity<Map<String, Object>> getAccountDetails( @PathVariable String token) {
		return customerAccountService.getCustomerAcountDetails(token);
	}

}
