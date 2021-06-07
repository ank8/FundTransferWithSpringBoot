package springboot.usecase.one.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class PaymentTxnRequest {
	@NotNull(message = "Customer Id is Mandatory")
	private long custId;

	@NotNull(message = "Customer Account Id is Mandatory")
	private long custAccId;

	@NotNull(message = "Customer Account Beneficiary Id is Mandatory")
	private long custAccBenId;

	private String remark;

	@NotNull(message = "Amount is Mandatory")
	private BigDecimal amt;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

}
