package springboot.usecase.one.models;

import java.util.Date;

public class PaymentTxnResponse {
	private String payerName;
	private String payerbank;
	private String payerAcc;
	private String benefeciaryName;
	private String benefeciarybank;
	private String benefeciaryAcc;
	private String trackingId;
	private String txnAmt;
	private Date txnDate;
	private String status;

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerbank() {
		return payerbank;
	}

	public void setPayerbank(String payerbank) {
		this.payerbank = payerbank;
	}

	public String getPayerAcc() {
		return payerAcc;
	}

	public void setPayerAcc(String payerAcc) {
		this.payerAcc = payerAcc;
	}

	public String getBenefeciaryName() {
		return benefeciaryName;
	}

	public void setBenefeciaryName(String benefeciaryName) {
		this.benefeciaryName = benefeciaryName;
	}

	public String getBenefeciarybank() {
		return benefeciarybank;
	}

	public void setBenefeciarybank(String benefeciarybank) {
		this.benefeciarybank = benefeciarybank;
	}

	public String getBenefeciaryAcc() {
		return benefeciaryAcc;
	}

	public void setBenefeciaryAcc(String benefeciaryAcc) {
		this.benefeciaryAcc = benefeciaryAcc;
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

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
