package springboot.usecase.one.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_FT_BANK_CTLG", uniqueConstraints = @UniqueConstraint(columnNames = { "BANK_CD" }))
public class BankCatalogEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "BANK_NAM")
	private String bankName;

	@NotNull
	@Column(name = "BANK_CD")
	private Long bankCode;

	@NotNull
	@Column(name = "IS_ACTIVE")
	private String active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getBankCode() {
		return bankCode;
	}

	public void setBankCode(Long bankCode) {
		this.bankCode = bankCode;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "BankCatalog [id=" + id + ", bankName=" + bankName + ", bankCode=" + bankCode + ", active=" + active
				+ "]";
	}

	protected BankCatalogEntity() {
		super();
	}

	public BankCatalogEntity(Long id, String bankName, Long bankCode, String active) {
		super();
		this.id = id;
		this.bankName = bankName;
		this.bankCode = bankCode;
		this.active = active;
	}

}
