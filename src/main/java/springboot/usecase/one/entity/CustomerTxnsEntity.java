package springboot.usecase.one.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_FT_CUST_TXNS")
public class CustomerTxnsEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	@NotNull
	private CustomerEntity customer;

	@ManyToOne
	@JoinColumn(name = "CUST_ACNT_ID")
	@NotNull
	private CustomerAccEntity customerAccount;

	@ManyToOne
	@JoinColumn(name = "CUST_BENF_ACNT_ID")
	@NotNull
	private CustomerAccBeneficiaryEntity customerBeneficiaryAccount;

	@NotNull
	@Column(name = "TXN_AMT")
	private BigDecimal txnAmt;

	@Column(name = "TXN_RMRK")
	private String txnRemark;

	@NotNull
	@Column(name = "TRAK_ID")
	private String trackingId;

	@NotNull
	@Column(name = "TXN_DT")
	private Date txnDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public CustomerAccEntity getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(CustomerAccEntity customerAccount) {
		this.customerAccount = customerAccount;
	}

	public CustomerAccBeneficiaryEntity getCustomerBeneficiaryAccount() {
		return customerBeneficiaryAccount;
	}

	public void setCustomerBeneficiaryAccount(CustomerAccBeneficiaryEntity customerBeneficiaryAccount) {
		this.customerBeneficiaryAccount = customerBeneficiaryAccount;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnRemark() {
		return txnRemark;
	}

	public void setTxnRemark(String txnRemark) {
		this.txnRemark = txnRemark;
	}

	
}
