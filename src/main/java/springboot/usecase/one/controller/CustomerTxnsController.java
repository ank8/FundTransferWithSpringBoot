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
import springboot.usecase.one.models.GetPaymentTxnRequest;
import springboot.usecase.one.models.PaymentTxnRequest;
import springboot.usecase.one.service.CustomerTxnsService;

@RestController
@RequestMapping(CommonConstants.BASE_PATH)
public class CustomerTxnsController {

	@Autowired
	CustomerTxnsService customerTxnsService;

	@PostMapping(CommonConstants.CUSTOMER_TXN_PATH)
	public ResponseEntity<Map<String, Object>> payCustomer(@RequestHeader(CommonConstants.TOKEN) String token,
			@Valid @RequestBody PaymentTxnRequest paymentTxnRequest) {
		return customerTxnsService.payCustomer(token, paymentTxnRequest);

	}

	@PostMapping(CommonConstants.CUSTOMER_TXN_PATH + CommonConstants.HISTORY)
	public ResponseEntity<Map<String, Object>> getTransaction(@RequestHeader(CommonConstants.TOKEN) String token,
			@RequestBody GetPaymentTxnRequest getPaymentTxnRequest) {
		return customerTxnsService.getTransaction(token, getPaymentTxnRequest);
	}

}
