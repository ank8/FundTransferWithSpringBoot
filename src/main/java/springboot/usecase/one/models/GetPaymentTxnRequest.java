package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;

public class GetPaymentTxnRequest {
	@NotEmpty(message = "Customer Id is Mandatory")
	private long custId;

	@NotEmpty(message = "Customer Account Id is Mandatory")
	private long custAccId;

	private long custAccBenId;

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public long getCustAccId() {
		return custAccId;
	}

	public void setCustAccId(long custAccId) {
		this.custAccId = custAccId;
	}

	public long getCustAccBenId() {
		return custAccBenId;
	}

	public void setCustAccBenId(long custAccBenId) {
		this.custAccBenId = custAccBenId;
	}

}
