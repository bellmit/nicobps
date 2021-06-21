package obps.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaProcessFlow implements Serializable {
	private static final long serialVersionUID = 6L;

	private String applicationcode;
	private Integer fromusercode;
	private Integer tousercode;
	private Integer fromprocesscode;
	private Integer toprocesscode;
	private String remarks;

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public Integer getFromusercode() {
		return fromusercode;
	}

	public void setFromusercode(Integer fromusercode) {
		this.fromusercode = fromusercode;
	}

	public Integer getTousercode() {
		return tousercode;
	}

	public void setTousercode(Integer tousercode) {
		this.tousercode = tousercode;
	}

	public Integer getFromprocesscode() {
		return fromprocesscode;
	}

	public void setFromprocesscode(Integer fromprocesscode) {
		this.fromprocesscode = fromprocesscode;
	}

	public Integer getToprocesscode() {
		return toprocesscode;
	}

	public void setToprocesscode(Integer toprocesscode) {
		this.toprocesscode = toprocesscode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "BpaProcessFlow [applicationcode=" + applicationcode + ", fromusercode=" + fromusercode + ", tousercode="
				+ tousercode + ", fromprocesscode=" + fromprocesscode + ", toprocesscode=" + toprocesscode
				+ ", remarks=" + remarks + "]";
	}

}
