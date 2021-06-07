package springboot.usecase.one.constants;

import springboot.usecase.one.constants.StatusMsgConstants.ErrorCode;
import springboot.usecase.one.exception.CustomExceptionHandler;

public class CommonConstants {

	private CommonConstants() {
		throw new CustomExceptionHandler(ErrorCode.ERR_CD_803,StatusMsgConstants.COM_CONST_EXCEP);
	}

	public static final String BASE_PATH = "/v2/api";
	public static final String CUSTOMER_PATH = "/customer";
	public static final String CUSTOMER_LOGIN_PATH = "/customer/login";
	public static final String CUSTOMER_ACNT_PATH = "/customer-acnt";
	public static final String CUSTOMER_ACNT_BEN_PATH = "/customer-acnt-benf";
	public static final String CUSTOMER_TXN_PATH = "/customer-txn";
	public static final String BANK_PATH = "/bank";
	public static final String PATH_VARIABLE = "/{custId}";
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
	public static final int JWT_TOKEN_EXP = 60;
	public static final String TOKEN = "token";
	public static final String ACC = "/acc";
	public static final String HISTORY = "/hstry";
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	public static final String MSG = "msg";
	public static final String PERSONAL_INFO = "personalInfo";
	public static final String PAYER = "Payer";
	public static final String BENIFICIARY = "Benificiary";
	public static final String ACC_INFO = "accountInfo";
	public static final String RESPONSE = "response";
	public static final String BANK = "bank";
	public static final String SUCCESS = "success";
	public static final String ID = "id";
	public static final String ACTIVE = "active";
	public static final String A = "A";
	public static final String ACCOUNT_BALANCE = "accountBalance";
	public static final String CUSTOMER_ID = "custId";
	public static final String CUSTOMER = "accounts";
	public static final String BEN_BANK_CTLG = "benBankCatalog";
	public static final String BEN_BANK_IFSC = "benAccountIfsc";
	public static final String BEN_NAME = "benAccountName";
	public static final String CUSTOMER_ACC_ID = "custAccId";
	public static final String ACC_BEN_ID = "accBenId";	
	public static final String CUSTOMER_ACC_BEN = "accBenf";
	public static final String TXN_ID = "txnId";
	public static final String TXN_TRCK_ID = "txnTrackingId";
	public static final String TXN_AMT = "txnAmt";
	public static final String TXN_DAT = "txnDate";
}
