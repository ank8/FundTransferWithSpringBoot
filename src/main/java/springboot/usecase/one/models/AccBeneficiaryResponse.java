package springboot.usecase.one.models;

public class AccBeneficiaryResponse {
	private Long id;
	private Long bankId;
	private Long bankCd;
	private String bankName;
	private String benAccNumber;
	private String benAccName;
	private String benAccIfsc;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public String getBenAccNumber() {
		return benAccNumber;
	}

	public void setBenAccNumber(String benAccNumber) {
		this.benAccNumber = benAccNumber;
	}

	public String getBenAccName() {
		return benAccName;
	}

	public void setBenAccName(String benAccName) {
		this.benAccName = benAccName;
	}

	public String getBenAccIfsc() {
		return benAccIfsc;
	}

	public void setBenAccIfsc(String benAccIfsc) {
		this.benAccIfsc = benAccIfsc;
	}

	public Long getBankCd() {
		return bankCd;
	}

	public void setBankCd(Long bankCd) {
		this.bankCd = bankCd;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
