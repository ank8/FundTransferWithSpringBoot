package springboot.usecase.one.models;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

public class PaymentTxnRequest {
	@NotEmpty(message = "Token is Mandatory")
	private String token;
	@NotEmpty(message = "Mobile No is Mandatory")
	private String mob;
	
	private BigDecimal amt;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public PaymentTxnRequest(@NotEmpty(message = "Token is Mandatory") String token,
			@NotEmpty(message = "Mobile No is Mandatory") String mob, BigDecimal amt) {
		super();
		this.token = token;
		this.mob = mob;
		this.amt = amt;
	}
	
	

}
