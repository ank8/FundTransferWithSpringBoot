package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddAccBeneficiaryRequest {
	@NotNull(message = "Customer ID is Mandatory")
	private long custId;
	@NotNull(message = "Account ID is Mandatory")
	private long accId;
	@NotNull(message = "Beneficiary Bank is Mandatory")
	private Long bankId;
	@NotEmpty(message = "Beneficiary Account Number is Mandatory")
	private String benAccNumber;
	@NotEmpty(message = "Beneficiary Account Name is Mandatory")
	private String benAccName;
	@NotEmpty(message = "Beneficiary Ifsc is Mandatory")
	private String benAccIfsc;

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBenAccNumber() {
		return benAccNumber;
	}

	public void setBenAccNumber(String benAccNumber) {
		this.benAccNumber = benAccNumber;
	}

	public String getBenAccName() {
		return benAccName;
	}

	public void setBenAccName(String benAccName) {
		this.benAccName = benAccName;
	}

	public String getBenAccIfsc() {
		return benAccIfsc;
	}

	public void setBenAccIfsc(String benAccIfsc) {
		this.benAccIfsc = benAccIfsc;
	}

}
