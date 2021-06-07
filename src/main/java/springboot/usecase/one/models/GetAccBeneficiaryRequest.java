package springboot.usecase.one.models;

import javax.validation.constraints.NotNull;

public class GetAccBeneficiaryRequest {
	@NotNull(message = "Customer ID is Mandatory")
	private Long custId;
	@NotNull(message = "Account ID is Mandatory")
	private Long accId;
	private Long accBenId;

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

	public Long getAccBenId() {
		return accBenId;
	}

	public void setAccBenId(Long accBenId) {
		this.accBenId = accBenId;
	}

}
