package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddAccountRequest {
	@NotEmpty(message = "Account Number is Mandatory")
	private String accountNumber;
	@NotEmpty(message = "Ifsc is Mandatory")
	private String accountIfsc;
	@NotNull(message = "Bank Code is Mandatory")
	private Long bankCode;
	@NotEmpty(message = "Token is Mandatory")
	private String token;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public AddAccountRequest(@NotEmpty(message = "Account Number is Mandatory") String accountNumber,
			@NotEmpty(message = "Ifsc is Mandatory") String accountIfsc,
			@NotNull(message = "Bank Code is Mandatory") Long bankCode,
			@NotEmpty(message = "Token is Mandatory") String token) {
		super();
		this.accountNumber = accountNumber;
		this.accountIfsc = accountIfsc;
		this.bankCode = bankCode;
		this.token = token;
	}

}
