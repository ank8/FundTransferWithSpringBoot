package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddAccountRequest {
	@NotNull(message = "Customer is Mandatory")
	private long custId;
	@NotEmpty(message = "Account Number is Mandatory")
	private String accountNumber;
	@NotEmpty(message = "Ifsc is Mandatory")
	private String accountIfsc;
	@NotNull(message = "Bank Code is Mandatory")
	private Long bankCode;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountIfsc() {
		return accountIfsc;
	}

	public void setAccountIfsc(String accountIfsc) {
		this.accountIfsc = accountIfsc;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

}
