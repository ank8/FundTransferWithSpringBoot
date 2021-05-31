package springboot.usecase.one.models;

public class PaymentTxnModel {
	private String payerName;
	private String payerbank;
	private String benefeciaryName;
	private String benefeciarybank;
	private String benefeciaryMob;
	private String trackingId;
	private String txnAmt;
	private String status;

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBenefeciaryName() {
		return benefeciaryName;
	}

	public void setBenefeciaryName(String benefeciaryName) {
		this.benefeciaryName = benefeciaryName;
	}

	public String getBenefeciaryMob() {
		return benefeciaryMob;
	}

	public void setBenefeciaryMob(String benefeciaryMob) {
		this.benefeciaryMob = benefeciaryMob;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayerbank() {
		return payerbank;
	}

	public void setPayerbank(String payerbank) {
		this.payerbank = payerbank;
	}

	public String getBenefeciarybank() {
		return benefeciarybank;
	}

	public void setBenefeciarybank(String benefeciarybank) {
		this.benefeciarybank = benefeciarybank;
	}

}
