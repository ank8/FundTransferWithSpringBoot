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
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.service.CustomerTxnsService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class CustomerTxnsController {

	@Autowired
	CustomerTxnsService customerTxnsService;

	@PostMapping(CommonConstants.CUSTOMER_TXN_PATH)
	public String payCustomer(@Valid @RequestBody PaymentTxnRequest paymentTxnRequest) {
		return customerTxnsService.payCustomer(paymentTxnRequest);

	}

	@GetMapping(CommonConstants.CUSTOMER_TXN_PATH + CommonConstants.PATH_VARIABLE)
	public ResponseEntity<Map<String, Object>> getTransaction(@PathVariable String token) {
		return customerTxnsService.getTransaction(token);

	}

}
