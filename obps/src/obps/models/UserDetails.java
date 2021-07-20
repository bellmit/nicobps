package obps.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetails {

	private String usercode;
	private String officecode;
	private String office;
	private String username;
	private String fullname;
	private String mobileno;
	private String designation;
	
	
	private String applicantsname;
	private String presentaddress;
	private String permanentaddress;
	private String licenseetypename;	
	

	public UserDetails() {
	}


	public String getUsercode() {
		return usercode;
	}


	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}


	public String getOfficecode() {
		return officecode;
	}


	public void setOfficecode(String officecode) {
		this.officecode = officecode;
	}


	public String getOffice() {
		return office;
	}


	public void setOffice(String office) {
		this.office = office;
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


	public String getApplicantsname() {
		return applicantsname;
	}


	public void setApplicantsname(String applicantsname) {
		this.applicantsname = applicantsname;
	}


	public String getPresentaddress() {
		return presentaddress;
	}


	public void setPresentaddress(String presentaddress) {
		this.presentaddress = presentaddress;
	}


	public String getPermanentaddress() {
		return permanentaddress;
	}


	public void setPermanentaddress(String permanentaddress) {
		this.permanentaddress = permanentaddress;
	}


	public String getLicenseetypename() {
		return licenseetypename;
	}


	public void setLicenseetypename(String licenseetypename) {
		this.licenseetypename = licenseetypename;
	}


	@Override
	public String toString() {
		return "UserDetails [usercode=" + usercode + ", officecode=" + officecode + ", office=" + office + ", username="
				+ username + ", fullname=" + fullname + ", mobileno=" + mobileno + ", designation=" + designation
				+ ", applicantsname=" + applicantsname + ", presentaddress=" + presentaddress + ", permanentaddress="
				+ permanentaddress + ", licenseetypename=" + licenseetypename + "]";
	}
	
	

}
