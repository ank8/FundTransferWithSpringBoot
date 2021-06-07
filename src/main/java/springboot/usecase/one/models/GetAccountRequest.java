package springboot.usecase.one.models;

import javax.validation.constraints.NotNull;

public class GetAccountRequest {
	@NotNull(message = "Customer is Mandatory")
	private long custId;

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

}
