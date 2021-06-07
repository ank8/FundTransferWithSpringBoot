package springboot.usecase.one.entity;

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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_FT_CUST_ACNT_BENF", uniqueConstraints = @UniqueConstraint(columnNames = { "CUST_ID", "ACNT_ID",
		"BENF_ACNT_NUM" }))
public class CustomerAccBeneficiaryEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "CUST_ID")
	@NotNull
	private CustomerEntity customer;

	@ManyToOne
	@JoinColumn(name = "ACNT_ID")
	@NotNull
	private CustomerAccEntity customerAccount;

	@OneToOne
	@JoinColumn(name = "BENF_BANK_ID")
	@NotNull
	private BankCatalogEntity benBankCatalog;

	@Column(name = "BENF_ACNT_NUM")
	@NotNull
	private String benAccountNumber;

	@Column(name = "BENF_ACNT_IFS")
	@NotNull
	private String benAccountIfsc;

	@Column(name = "BENF_ACNT_NAM")
	@NotNull
	private String benAccountName;

	@OneToMany(mappedBy = "customerBeneficiaryAccount", fetch = FetchType.LAZY)
	List<CustomerTxnsEntity> customerTxnsEntity;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public BankCatalogEntity getBenBankCatalog() {
		return benBankCatalog;
	}

	public void setBenBankCatalog(BankCatalogEntity benBankCatalog) {
		this.benBankCatalog = benBankCatalog;
	}

	public String getBenAccountNumber() {
		return benAccountNumber;
	}

	public void setBenAccountNumber(String benAccountNumber) {
		this.benAccountNumber = benAccountNumber;
	}

	public String getBenAccountIfsc() {
		return benAccountIfsc;
	}

	public void setBenAccountIfsc(String benAccountIfsc) {
		this.benAccountIfsc = benAccountIfsc;
	}

	public String getBenAccountName() {
		return benAccountName;
	}

	public void setBenAccountName(String benAccountName) {
		this.benAccountName = benAccountName;
	}

	public List<CustomerTxnsEntity> getCustomerTxnsEntity() {
		return customerTxnsEntity;
	}

	public void setCustomerTxnsEntity(List<CustomerTxnsEntity> customerTxnsEntity) {
		this.customerTxnsEntity = customerTxnsEntity;
	}

	
}
