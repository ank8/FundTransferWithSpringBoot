package springboot.usecase.one.constants;

import springboot.usecase.one.exception.CustomExceptionHandler;

public class CommonConstants {

	private CommonConstants() {
		throw new CustomExceptionHandler("CommonConstants class exception");
	}

	public static final String BASE_PATH = "/v1/api";
	public static final String CUSTOMER_PATH = "/customer";
	public static final String CUSTOMER_LOGIN_PATH = "/customer/login";
	public static final String CUSTOMER_ACNT_PATH = "/customer-acnt";
	public static final String CUSTOMER_TXN_PATH = "/customer-txn";
	public static final String BANK_PATH = "/bank";
	public static final String PATH_VARIABLE = "/{token}";
	public static final String DELETE_PATH = "/{id}";
	public static final String PAGE = "/page/size";
	public static final String SORT = "/sort";
	public static final String SHA512 = "SHA-512";
	public static final String JWT_TOKEN_KEY = "A1h2u9u5x5";
	public static final Long DEFAULT_AMT = 2000l;
	public static final String TRK = "TRK";
	public static final String CREDIT = "Credit";
	public static final String DEBIT = "Debit";
	public static final String TOTAL_TXN = "totalTxns";
	public static final String TXN = "txns";
	public static final int JWT_TOKEN_EXP = 15;

}
