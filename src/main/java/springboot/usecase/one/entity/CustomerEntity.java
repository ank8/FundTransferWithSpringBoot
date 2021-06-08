package springboot.usecase.one.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_FT_CUST_DATA")
public class CustomerEntity {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "CUST_EML")
	@NotNull
	private String email;

	@Column(name = "CUST_PAS")
	@NotNull
	private String pwd;

	@Column(name = "CUST_NAM")
	@NotNull
	private String name;

	@Column(name = "CUST_MOB")
	@NotNull
	private String mobile;

	@Column(name = "CUST_DOB")
	private String dob;

	@Column(name = "CUST_ADD")
	private String address;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	List<CustomerAccEntity> customerAccount;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	List<CustomerTxnsEntity> customerTxnsEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CustomerAccEntity> getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(List<CustomerAccEntity> customerAccount) {
		this.customerAccount = customerAccount;
	}

	public List<CustomerTxnsEntity> getCustomerTxnsEntity() {
		return customerTxnsEntity;
	}

	public void setCustomerTxnsEntity(List<CustomerTxnsEntity> customerTxnsEntity) {
		this.customerTxnsEntity = customerTxnsEntity;
	}

}
