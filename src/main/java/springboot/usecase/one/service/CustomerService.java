package springboot.usecase.one.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.entity.CustomerAccEntity;
import springboot.usecase.one.entity.CustomerEntity;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.AddCustomerRequest;
import springboot.usecase.one.models.CommonResponse;
import springboot.usecase.one.models.CustomerAccResponse;
import springboot.usecase.one.models.CustomerInfoResponse;
import springboot.usecase.one.models.LoginCustomerRequest;
import springboot.usecase.one.repository.BankCatalogRepository;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.CommonUtility;
import springboot.usecase.one.utility.JwtToken;
import springboot.usecase.one.utility.PaymentServiceUtility;

@Service
public class CustomerService {
	private static final Logger LOGGER = LogManager.getLogger(CustomerService.class);

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	BankCatalogRepository bankCatalogRepository;

	@Autowired
	PaymentServiceUtility paymentServiceUtility;

	public ResponseEntity<Map<String, Object>> addCustomer(AddCustomerRequest customerMdl)
			throws NoSuchAlgorithmException {
		CustomerEntity customer = new CustomerEntity();
		BeanUtils.copyProperties(customerMdl, customer);
		Optional<CustomerEntity> customerData = customerRepository.findByMobileOrEmail(customer.getMobile(),
				customer.getEmail());
		if (customerData.isPresent()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_716, StatusMsgConstants.DUPLICATE_ENTRY_NOT_ALLOWED);
			}
			throw new CustomExceptionHandler(ErrorCode.ERR_CD_716, StatusMsgConstants.DUPLICATE_ENTRY_NOT_ALLOWED);
		}
		customer.setPwd(CommonUtility.getSHA512(customer.getPwd()));
		paymentServiceUtility.addCustomerAccount(customer);
		Map<String, Object> map = new HashMap<>();
		map.put(CommonConstants.RESPONSE,
				new CommonResponse(ErrorCode.ERR_CD_717, StatusMsgConstants.REGISTER_SUCCESS));
		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	public ResponseEntity<Map<String, Object>> loginValidate(LoginCustomerRequest login)
			throws NoSuchAlgorithmException {
		Map<String, Object> map = new HashMap<>();
		String pwd = login.getPwd();
		pwd = CommonUtility.getSHA512(pwd);
		login.setPwd(pwd);
		Optional<CustomerEntity> custList = customerRepository.findByPwdAndEmail(pwd, login.getEmail());
		if (custList.isPresent()) {
			CustomerEntity customer = custList.get();
			CustomerInfoResponse model = new CustomerInfoResponse();
			BeanUtils.copyProperties(customer, model);
			map.put(CommonConstants.RESPONSE,
					new CommonResponse(ErrorCode.ERR_CD_718, StatusMsgConstants.LOGIN_SUCCESS));
			map.put(CommonConstants.PERSONAL_INFO, model);
			map.put(CommonConstants.TOKEN, JwtToken.createToken(customer.getId(), CommonConstants.JWT_TOKEN_EXP));
			List<CustomerAccEntity> acc = customer.getCustomerAccount();
			List<CustomerAccResponse> listAcc = new ArrayList<>();
			acc.stream().forEach(data -> {
				CustomerAccResponse accmodel = new CustomerAccResponse();
				BeanUtils.copyProperties(data, accmodel);
				accmodel.setBankCode(data.getBankCatalog().getBankCode());
				listAcc.add(accmodel);
			});
			map.put(CommonConstants.ACC_INFO, listAcc);
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(ErrorCode.ERR_CD_719, StatusMsgConstants.LOGIN_FAIL);
			}
			map.put(CommonConstants.RESPONSE, new CommonResponse(ErrorCode.ERR_CD_719, StatusMsgConstants.LOGIN_FAIL));
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
