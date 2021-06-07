package springboot.usecase.one.models;

import java.math.BigDecimal;

public class CustomerAccResponse {
	private Long id;

	private Long bankCode;

	private String accountNumber;

	private String accountIfsc;

	private BigDecimal accountBalance;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public long getBankCode() {
		return bankCode;
	}

	public void setBankCode(long bankCode) {
		this.bankCode = bankCode;
	}

}
