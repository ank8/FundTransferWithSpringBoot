package springboot.usecase.one.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import springboot.usecase.one.constants.CommonConstants;
import springboot.usecase.one.constants.StatusMsgConstants;
import springboot.usecase.one.entity.Customer;
import springboot.usecase.one.entity.CustomerAccount;
import springboot.usecase.one.exception.CustomExceptionHandler;
import springboot.usecase.one.models.CustomerModel;
import springboot.usecase.one.models.LoginRequest;
import springboot.usecase.one.repository.CustomerAccountRepository;
import springboot.usecase.one.repository.CustomerRepository;
import springboot.usecase.one.utility.CommonUtility;
import springboot.usecase.one.utility.JwtToken;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	public String addCustomer(CustomerModel customerMdl) throws NoSuchAlgorithmException {
		Customer customer = modelToCustomer(customerMdl);
		Optional<Customer> customerData = customerRepository.findByMobileOrEmail(customer.getMobile(),
				customer.getEmail());
		if (customerData.isPresent()) {
			throw new CustomExceptionHandler(StatusMsgConstants.DUPLICATE_ENTRY_NOT_ALLOWED);
		}
		String pwd = customer.getPwd();
		pwd = CommonUtility.getSHA512(pwd);
		customer.setPwd(pwd);
		return customerRepository.save(customer).getId() != null ? StatusMsgConstants.REGISTER_SUCCESS
				: StatusMsgConstants.REGISTER_FAIL;
	}

	public ResponseEntity<Map<String, Object>> loginValidate(LoginRequest login) throws NoSuchAlgorithmException {
		Map<String, Object> map = new HashMap<>();
		String pwd = login.getPwd();
		pwd = CommonUtility.getSHA512(pwd);
		login.setPwd(pwd);
		Optional<Customer> custList = customerRepository.findByPwdAndEmail(pwd, login.getEmail());
		if (custList.isPresent()) {
			custList.stream().forEach(data -> {
				map.put(StatusMsgConstants.MSG, StatusMsgConstants.LOGIN_SUCCESS);
				map.put(StatusMsgConstants.PERSONAL_INFO, data);
				map.put(StatusMsgConstants.TOKEN, JwtToken.createToken(data.getId(),CommonConstants.JWT_TOKEN_EXP));
				CustomerAccount acc = customerAccountRepository.findByCustId(data.getId());
				map.put(StatusMsgConstants.ACC_INFO, acc);
			});
		} else {
			map.put(StatusMsgConstants.MSG, StatusMsgConstants.LOGIN_FAIL);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	private Customer modelToCustomer(CustomerModel customerMdl) {
		return new Customer(customerMdl.getId(), customerMdl.getEmail(), customerMdl.getPwd(), customerMdl.getName(),
				customerMdl.getMobile(), customerMdl.getDob(), customerMdl.getAddress());
	}
}
