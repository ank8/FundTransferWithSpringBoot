package springboot.usecase.one.models;

import javax.validation.constraints.NotNull;

public class BankCatalogModel {

	private Long id;

	@NotNull
	private String bankName;

	@NotNull
	private Long bankCode;

	@NotNull
	private String active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "BankCatalog [id=" + id + ", bankName=" + bankName + ", bankCode=" + bankCode + ", active=" + active
				+ "]";
	}

	public BankCatalogModel(Long id, String bankName, Long bankCode, String active) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.active = active;
	}

}
