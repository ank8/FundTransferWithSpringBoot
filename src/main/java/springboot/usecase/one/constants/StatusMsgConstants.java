package springboot.usecase.one.constants;

import springboot.usecase.one.exception.CustomExceptionHandler;

public class StatusMsgConstants {
	private StatusMsgConstants() {
		throw new CustomExceptionHandler("804","StatusMsgConstants class exception");
	}
	public static final String INVALID_TOKEN = "Invalid Token";
	public static final String ACC_ADD_SUCCESS = "Account Added Successfully";
	public static final String INTERNAL_ERR = "Intrenal Process Failed";
	public static final String REGISTER_SUCCESS = "Successfully Registered";
	public static final String REGISTER_FAIL = "Registration Failed";
	public static final String LOGIN_FAIL = "Login Failed";
	public static final String LOGIN_SUCCESS = "Login Successfull";
	public static final String MSG = "msg";
	public static final String PERSONAL_INFO = "personalInfo";
	public static final String PAYER = "Payer";
	public static final String BENIFICIARY = "Benificiary";
	public static final String TOKEN = "token";
	public static final String ACC_INFO = "accountInfo";
	public static final String PAYER_ACC_NOT_FOUND = "Payer Account Not Found";
	public static final String BEN_NOT_FOUND = "Benificiary Not Found";
	public static final String BEN_ACC_NOT_FOUND = "Benificiary Account Not Found";
	public static final String INSUFFICIENT_BAL = "Insufficient Fund";
	public static final String TXN_SUCCESS = "Transaction Successfull";
	public static final String AMT_SHOULD_GREATER_THHEN_ZERO = "Amount should be greater then 0";
	public static final String BANK_NOT_EXIST = "Bank Does not Exist";
	public static final String BANK_ALREADY_EXIST =  "Bank Already Exist";
	public static final String DELETE_SUCCESS = "Successfully Deleted";
	public static final String DELETE_FAILED = "Delete Failed";
	public static final String BANK_INACTIVE = "Bank is currently unavailable";
	public static final String RECORD_ALREADY_EXIST = "Record Already Exist";
	public static final String DUPLICATE_ENTRY_NOT_ALLOWED = "Duplicate entry not allowed";
	
}
