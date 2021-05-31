package springboot.usecase.one.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "TB_CUST_DATA")
public class Customer {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "CUST_EML")
	@NotEmpty(message = "Email is Mandatory")
	private String email;
	@Column(name = "CUST_PAS")
	@NotEmpty(message = "Password is Mandatory")
	private String pwd;
	@Column(name = "CUST_NAM")
	@NotEmpty(message = "Name is Mandatory")
	private String name;
	@Column(name = "CUST_MOB")
	@NotEmpty(message = "Mobile No is Mandatory")
	private String mobile;
	@Column(name = "CUST_DOB")
	private String dob;
	@Column(name = "CUST_ADD")
	private String address;

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
	
	

	protected Customer() {
		super();
	}

	public Customer(Long id, @NotEmpty(message = "Email is Mandatory") String email,
			@NotEmpty(message = "Password is Mandatory") String pwd,
			@NotEmpty(message = "Name is Mandatory") String name,
			@NotEmpty(message = "Mobile No is Mandatory") String mobile, String dob, String address) {
		super();
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.name = name;
		this.mobile = mobile;
		this.dob = dob;
		this.address = address;
	}

	

}
