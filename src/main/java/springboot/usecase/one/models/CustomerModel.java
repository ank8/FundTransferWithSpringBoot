package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;

public class CustomerModel {
	private Long id;
	@NotEmpty(message = "Email is Mandatory")
	private String email;
	@NotEmpty(message = "Password is Mandatory")
	private String pwd;
	@NotEmpty(message = "Name is Mandatory")
	private String name;
	@NotEmpty(message = "Mobile No is Mandatory")
	private String mobile;
	private String dob;
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

	public CustomerModel(Long id, @NotEmpty(message = "Email is Mandatory") String email,
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
