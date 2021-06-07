package springboot.usecase.one.constants;

import springboot.usecase.one.exception.CustomExceptionHandler;

public class StatusMsgConstants {

	private StatusMsgConstants() {
		throw new CustomExceptionHandler(ErrorCode.ERR_CD_804, StatusMsgConstants.COM_STS_CONST_EXCEP);
	}

	public static final String INVALID_TOKEN = "Invalid Token";
	public static final String ACC_ADD_SUCCESS = "Account Added Successfully";
	public static final String INTERNAL_ERR = "Intrenal Process Failed";
	public static final String REGISTER_SUCCESS = "Successfully Registered";
	public static final String REGISTER_FAIL = "Registration Failed";
	public static final String LOGIN_FAIL = "Login Failed";
	public static final String LOGIN_SUCCESS = "Login Successfull";
	public static final String PAYER_ACC_NOT_FOUND = "Payer Account Not Found";
	public static final String BEN_NOT_FOUND = "Benificiary Not Found";
	public static final String BEN_ACC_NOT_FOUND = "Benificiary Account Not Found";
	public static final String INSUFFICIENT_BAL = "Insufficient Fund";
	public static final String TXN_SUCCESS = "Transaction Successfull";
	public static final String AMT_SHOULD_GREATER_THHEN_ZERO = "Amount should be greater then 0";
	public static final String BANK_NOT_EXIST = "Bank Does not Exist";
	public static final String BANK_ALREADY_EXIST = "Bank Already Exist";
	public static final String DELETE_SUCCESS = "Successfully Deleted";
	public static final String DELETE_FAILED = "Delete Failed";
	public static final String BANK_INACTIVE = "Bank is currently unavailable";
	public static final String RECORD_ALREADY_EXIST = "Record Already Exist";
	public static final String DUPLICATE_ENTRY_NOT_ALLOWED = "Duplicate entry not allowed";
	public static final String BANK_ADDED_SUCCESS = "Bank Added Successfully";
	public static final String BANK_UPDATED_SUCCESS = "Bank Updated Successfully";
	public static final String BANK_ACC_ADDED_SUCCESS = "Bank Account Added Successfully";
	public static final String NO_ACTIVE_BANK_AVALABLE = "No Active Bank Available";
	public static final String CUSTOMER_ACCOUNT_NOT_FOUND = "Customer Account Not found";
	public static final String CUSTOMER_NOT_FOUND = "Customer Not found";
	public static final String BANK_NOT_FOUND = "Bank Not Found";
	public static final String BEN_ALREADY_EXIST = "Beneficiary Account Already Exist For This Customer";
	public static final String BEN_ACC_ADDED_SUCCESS = "Beneficiary Account Added Successfully";
	public static final String TXN_FAILED = "Transaction Failed";
	public static final String COM_UTIL_EXCP = "CommonUtility class exception";
	public static final String JWT_TKN_EXCP = "JwtToken class exception";
	public static final String COM_CONST_EXCEP = "CommonConstants class exception";
	private static final String COM_STS_CONST_EXCEP = "StatusMsgConstants class exception";
	public static final String ERR_CONST_EXCP = "ErrorCode class exception";

	public class ErrorCode {
		private ErrorCode() {
			throw new CustomExceptionHandler(ERR_CD_805, StatusMsgConstants.ERR_CONST_EXCP);
		}

		public static final String ERR_CD_000 = "000";
		public static final String ERR_CD_999 = "900";
		public static final String ERR_CD_801 = "801";
		public static final String ERR_CD_802 = "802";
		public static final String ERR_CD_803 = "803";
		public static final String ERR_CD_804 = "804";
		public static final String ERR_CD_805 = "805";
		public static final String ERR_CD_701 = "701";
		public static final String ERR_CD_702 = "702";
		public static final String ERR_CD_703 = "703";
		public static final String ERR_CD_704 = "704";
		public static final String ERR_CD_705 = "705";
		public static final String ERR_CD_706 = "706";
		public static final String ERR_CD_707 = "707";
		public static final String ERR_CD_708 = "708";
		public static final String ERR_CD_709 = "709";
		public static final String ERR_CD_710 = "710";
		public static final String ERR_CD_711 = "711";
		public static final String ERR_CD_712 = "712";
		public static final String ERR_CD_713 = "713";
		public static final String ERR_CD_714 = "714";
		public static final String ERR_CD_715 = "715";
		public static final String ERR_CD_716 = "716";
		public static final String ERR_CD_717 = "717";
		public static final String ERR_CD_718 = "718";
		public static final String ERR_CD_719 = "719";
		public static final String ERR_CD_720 = "720";
		public static final String ERR_CD_721 = "721";
		public static final String ERR_CD_722 = "722";
		public static final String ERR_CD_723 = "723";
		public static final String ERR_CD_724 = "724";
		public static final String ERR_CD_725 = "725";
		public static final String ERR_CD_726 = "726";
		public static final String ERR_CD_727 = "727";
		public static final String ERR_CD_728 = "728";
		public static final String ERR_CD_729 = "729";
		public static final String ERR_CD_730 = "730";
		public static final String ERR_CD_731 = "731";
		public static final String ERR_CD_732 = "732";
		public static final String ERR_CD_733 = "733";
		public static final String ERR_CD_734 = "734";
		public static final String ERR_CD_735 = "735";
		public static final String ERR_CD_736 = "736";
		public static final String ERR_CD_737 = "737";
		public static final String ERR_CD_738 = "738";
		public static final String ERR_CD_739 = "739";
		public static final String ERR_CD_740 = "740";
		public static final String ERR_CD_741 = "741";
		public static final String ERR_CD_742 = "742";
		public static final String ERR_CD_743 = "743";
		public static final String ERR_CD_744 = "744";
		public static final String ERR_CD_745 = "745";
		public static final String ERR_CD_746 = "746";
		public static final String ERR_CD_747 = "747";
		public static final String ERR_CD_748 = "748";
		public static final String ERR_CD_749 = "749";
		public static final String ERR_CD_750 = "750";
		public static final String ERR_CD_751 = "751";
		public static final String ERR_CD_752 = "752";
		public static final String ERR_CD_753 = "753";

	}
}
