package obps.models;

import java.util.Arrays;
import java.util.List;

public class Offices {
	private Integer officecode;
	private String officename1;
	private String officename2;
	private String officename3;
	private String officeshortname;
	private String officelgdcode;
	private String signatoryname;
	private String signatorydesignation;
	private String emailid;
	private String emailidpassword;
	private String smsusername;
	private String smspassword;
	private String smssenderid;
	private String isregisteringoffice;
	private Integer registeringofficecode;
	private String enabled;
	private String stateid;
	private String tenantid;
	private byte[] logo;
	private List<PaymentModes> mappedpaymentmodes;
	
	public Offices() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Offices(Integer officecode, String officename1, String officename2, String officename3,
			String officeshortname, String officelgdcode, String signatoryname, String signatorydesignation,
			String emailid, String emailidpassword, String smsusername, String smspassword, String smssenderid,
			String isregisteringoffice, Integer registeringofficecode, String enabled, String stateid, String tenantid,
			byte[] logo, List<PaymentModes> mappedpaymentmodes) {
		super();
		this.officecode = officecode;
		this.officename1 = officename1;
		this.officename2 = officename2;
		this.officename3 = officename3;
		this.officeshortname = officeshortname;
		this.officelgdcode = officelgdcode;
		this.signatoryname = signatoryname;
		this.signatorydesignation = signatorydesignation;
		this.emailid = emailid;
		this.emailidpassword = emailidpassword;
		this.smsusername = smsusername;
		this.smspassword = smspassword;
		this.smssenderid = smssenderid;
		this.isregisteringoffice = isregisteringoffice;
		this.registeringofficecode = registeringofficecode;
		this.enabled = enabled;
		this.stateid = stateid;
		this.tenantid = tenantid;
		this.logo = logo;
		this.mappedpaymentmodes = mappedpaymentmodes;
	}

	/**
	 * @return the officecode
	 */
	public Integer getOfficecode() {
		return officecode;
	}
	/**
	 * @param officecode the officecode to set
	 */
	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}
	/**
	 * @return the officename1
	 */
	public String getOfficename1() {
		return officename1;
	}
	/**
	 * @param officename1 the officename1 to set
	 */
	public void setOfficename1(String officename1) {
		this.officename1 = officename1;
	}
	/**
	 * @return the officename2
	 */
	public String getOfficename2() {
		return officename2;
	}
	/**
	 * @param officename2 the officename2 to set
	 */
	public void setOfficename2(String officename2) {
		this.officename2 = officename2;
	}
	/**
	 * @return the officename3
	 */
	public String getOfficename3() {
		return officename3;
	}
	/**
	 * @param officename3 the officename3 to set
	 */
	public void setOfficename3(String officename3) {
		this.officename3 = officename3;
	}
	/**
	 * @return the officeshortname
	 */
	public String getOfficeshortname() {
		return officeshortname;
	}
	/**
	 * @param officeshortname the officeshortname to set
	 */
	public void setOfficeshortname(String officeshortname) {
		this.officeshortname = officeshortname;
	}
	/**
	 * @return the officelgdcode
	 */
	public String getOfficelgdcode() {
		return officelgdcode;
	}
	/**
	 * @param officelgdcode the officelgdcode to set
	 */
	public void setOfficelgdcode(String officelgdcode) {
		this.officelgdcode = officelgdcode;
	}
	/**
	 * @return the signatoryname
	 */
	public String getSignatoryname() {
		return signatoryname;
	}
	/**
	 * @param signatoryname the signatoryname to set
	 */
	public void setSignatoryname(String signatoryname) {
		this.signatoryname = signatoryname;
	}
	/**
	 * @return the signatorydesignation
	 */
	public String getSignatorydesignation() {
		return signatorydesignation;
	}
	/**
	 * @param signatorydesignation the signatorydesignation to set
	 */
	public void setSignatorydesignation(String signatorydesignation) {
		this.signatorydesignation = signatorydesignation;
	}
	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}
	/**
	 * @param emailid the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	/**
	 * @return the emailidpassword
	 */
	public String getEmailidpassword() {
		return emailidpassword;
	}
	/**
	 * @param emailidpassword the emailidpassword to set
	 */
	public void setEmailidpassword(String emailidpassword) {
		this.emailidpassword = emailidpassword;
	}
	/**
	 * @return the smsusername
	 */
	public String getSmsusername() {
		return smsusername;
	}
	/**
	 * @param smsusername the smsusername to set
	 */
	public void setSmsusername(String smsusername) {
		this.smsusername = smsusername;
	}
	/**
	 * @return the smspassword
	 */
	public String getSmspassword() {
		return smspassword;
	}
	/**
	 * @param smspassword the smspassword to set
	 */
	public void setSmspassword(String smspassword) {
		this.smspassword = smspassword;
	}
	/**
	 * @return the smssenderid
	 */
	public String getSmssenderid() {
		return smssenderid;
	}
	/**
	 * @param smssenderid the smssenderid to set
	 */
	public void setSmssenderid(String smssenderid) {
		this.smssenderid = smssenderid;
	}
	/**
	 * @return the isregisteringoffice
	 */
	public String getIsregisteringoffice() {
		return isregisteringoffice;
	}
	/**
	 * @param isregisteringoffice the isregisteringoffice to set
	 */
	public void setIsregisteringoffice(String isregisteringoffice) {
		this.isregisteringoffice = isregisteringoffice;
	}
	/**
	 * @return the registeringofficecode
	 */
	public Integer getRegisteringofficecode() {
		return registeringofficecode;
	}
	/**
	 * @param registeringofficecode the registeringofficecode to set
	 */
	public void setRegisteringofficecode(Integer registeringofficecode) {
		this.registeringofficecode = registeringofficecode;
	}
	/**
	 * @return the enabled
	 */
	public String getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	
	
	public String getStateid() {
		return stateid;
	}

	public void setStateid(String stateid) {
		this.stateid = stateid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	/**
	 * @return the mappedpaymentmodes
	 */
	public List<PaymentModes> getMappedpaymentmodes() {
		return mappedpaymentmodes;
	}
	/**
	 * @param mappedpaymentmodes the mappedpaymentmodes to set
	 */
	public void setMappedpaymentmodes(List<PaymentModes> mappedpaymentmodes) {
		this.mappedpaymentmodes = mappedpaymentmodes;
	}

	@Override
	public String toString() {
		return "Offices [officecode=" + officecode + ", officename1=" + officename1 + ", officename2=" + officename2
				+ ", officename3=" + officename3 + ", officeshortname=" + officeshortname + ", officelgdcode="
				+ officelgdcode + ", signatoryname=" + signatoryname + ", signatorydesignation=" + signatorydesignation
				+ ", emailid=" + emailid + ", emailidpassword=" + emailidpassword + ", smsusername=" + smsusername
				+ ", smspassword=" + smspassword + ", smssenderid=" + smssenderid + ", isregisteringoffice="
				+ isregisteringoffice + ", registeringofficecode=" + registeringofficecode + ", enabled=" + enabled
				+ ", stateid=" + stateid + ", tenantid=" + tenantid + ", logo=" + Arrays.toString(logo)
				+ ", mappedpaymentmodes=" + mappedpaymentmodes + "]";
	}
	
	
	
}
