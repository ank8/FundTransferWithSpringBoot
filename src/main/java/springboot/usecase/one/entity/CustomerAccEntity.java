package springboot.usecase.one.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_FT_CUST_ACNT")
public class CustomerAccEntity {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "BANK_ID")
	@NotNull
	private BankCatalogEntity bankCatalog;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	private CustomerEntity customer;

	@Column(name = "ACNT_NUM")
	@NotNull
	private String accountNumber;

	@Column(name = "ACNT_IFS")
	@NotNull
	private String accountIfsc;

	@Column(name = "ACNT_BAL")
	private BigDecimal accountBalance;

	@OneToMany(mappedBy = "customerAccount", fetch = FetchType.LAZY)
	List<CustomerAccBeneficiaryEntity> customerBeneficiaryAccount;

	@OneToMany(mappedBy = "customerAccount", fetch = FetchType.LAZY)
	List<CustomerTxnsEntity> customerTxnsEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountIfsc() {
		return accountIfsc;
	}

	public void setAccountIfsc(String accountIfsc) {
		this.accountIfsc = accountIfsc;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public BankCatalogEntity getBankCatalog() {
		return bankCatalog;
	}

	public void setBankCatalog(BankCatalogEntity bankCatalog) {
		this.bankCatalog = bankCatalog;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public List<CustomerAccBeneficiaryEntity> getCustomerBeneficiaryAccount() {
		return customerBeneficiaryAccount;
	}

	public void setCustomerBeneficiaryAccount(List<CustomerAccBeneficiaryEntity> customerBeneficiaryAccount) {
		this.customerBeneficiaryAccount = customerBeneficiaryAccount;
	}

	public List<CustomerTxnsEntity> getCustomerTxnsEntity() {
		return customerTxnsEntity;
	}

	public void setCustomerTxnsEntity(List<CustomerTxnsEntity> customerTxnsEntity) {
		this.customerTxnsEntity = customerTxnsEntity;
	}

}
