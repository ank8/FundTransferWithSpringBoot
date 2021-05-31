package springboot.usecase.one.models;

import javax.validation.constraints.NotEmpty;

public class LoginRequest {
	@NotEmpty(message = "Email is Mandatory")
	private String email;
	@NotEmpty(message = "Password is Mandatory")
	private String pwd;

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

}
