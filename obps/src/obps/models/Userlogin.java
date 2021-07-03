package obps.models;

import java.util.List;

public class Userlogin {
	private Integer usercode;
	private String username;
	private String fullname;
	private String mobileno;
	private String designation;
	private String userpassword;
	private Integer officecode;
	private List<Pageurls> mappedpages;

	public Userlogin() {
		super();
	}

	public Integer getOfficecode() {
		return officecode;
	}

	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}

	public Integer getUsercode() {
		return usercode;
	}

	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public List<Pageurls> getMappedpages() {
		return mappedpages;
	}

	public void setMappedpages(List<Pageurls> mappedpages) {
		this.mappedpages = mappedpages;
	}

	@Override
	public String toString() {
		return "Userlogin [usercode=" + usercode + ", username=" + username + ", fullname=" + fullname + ", mobileno="
				+ mobileno + ", designation=" + designation + "]";
	}

}
