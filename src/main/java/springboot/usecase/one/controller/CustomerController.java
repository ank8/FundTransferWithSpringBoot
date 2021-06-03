package springboot.usecase.one.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.CustomerModel;
import springboot.usecase.one.models.LoginRequest;
import springboot.usecase.one.service.CustomerService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping(CommonConstants.CUSTOMER_PATH)
	public String customerRegister(@Valid @RequestBody CustomerModel customerMdl) {
		try {
			return customerService.addCustomer(customerMdl);
		} catch (NoSuchAlgorithmException e) {
			throw new CustomExceptionHandler("701",StatusMsgConstants.INTERNAL_ERR);
		}
	}

	@PostMapping(CommonConstants.CUSTOMER_LOGIN_PATH)
	public ResponseEntity<Map<String, Object>> loginValidation(@Valid @RequestBody LoginRequest login)
			throws NoSuchAlgorithmException {
		try {
			return customerService.loginValidate(login);
		} catch (NoSuchAlgorithmException e) {
			throw new CustomExceptionHandler("702",StatusMsgConstants.INTERNAL_ERR);
		}
	}
}
