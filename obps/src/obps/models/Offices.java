package obps.models;

public class Offices {

	private int officecode;
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
	private int registeringofficecode;
	private String enabled;
	
	
	
	public Offices() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getOfficecode() {
		return officecode;
	}
	public void setOfficecode(int officecode) {
		this.officecode = officecode;
	}
	public String getOfficename1() {
		return officename1;
	}
	public void setOfficename1(String officename1) {
		this.officename1 = officename1;
	}
	public String getOfficename2() {
		return officename2;
	}
	public void setOfficename2(String officename2) {
		this.officename2 = officename2;
	}
	public String getOfficename3() {
		return officename3;
	}
	public void setOfficename3(String officename3) {
		this.officename3 = officename3;
	}
	public String getOfficeshortname() {
		return officeshortname;
	}
	public void setOfficeshortname(String officeshortname) {
		this.officeshortname = officeshortname;
	}
	public String getOfficelgdcode() {
		return officelgdcode;
	}
	public void setOfficelgdcode(String officelgdcode) {
		this.officelgdcode = officelgdcode;
	}
	public String getSignatoryname() {
		return signatoryname;
	}
	public void setSignatoryname(String signatoryname) {
		this.signatoryname = signatoryname;
	}
	public String getSignatorydesignation() {
		return signatorydesignation;
	}
	public void setSignatorydesignation(String signatorydesignation) {
		this.signatorydesignation = signatorydesignation;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getEmailidpassword() {
		return emailidpassword;
	}
	public void setEmailidpassword(String emailidpassword) {
		this.emailidpassword = emailidpassword;
	}
	public String getSmsusername() {
		return smsusername;
	}
	public void setSmsusername(String smsusername) {
		this.smsusername = smsusername;
	}
	public String getSmspassword() {
		return smspassword;
	}
	public void setSmspassword(String smspassword) {
		this.smspassword = smspassword;
	}
	public String getSmssenderid() {
		return smssenderid;
	}
	public void setSmssenderid(String smssenderid) {
		this.smssenderid = smssenderid;
	}
	public String getIsregisteringoffice() {
		return isregisteringoffice;
	}
	public void setIsregisteringoffice(String isregisteringoffice) {
		this.isregisteringoffice = isregisteringoffice;
	}
	public int getRegisteringofficecode() {
		return registeringofficecode;
	}
	public void setRegisteringofficecode(int registeringofficecode) {
		this.registeringofficecode = registeringofficecode;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "Offices [officecode=" + officecode + ", officename1=" + officename1 + ", officename2=" + officename2
				+ ", officename3=" + officename3 + ", officeshortname=" + officeshortname + ", officelgdcode="
				+ officelgdcode + ", signatoryname=" + signatoryname + ", signatorydesignation=" + signatorydesignation
				+ ", emailid=" + emailid + ", emailidpassword=" + emailidpassword + ", smsusername=" + smsusername
				+ ", smspassword=" + smspassword + ", smssenderid=" + smssenderid + ", isregisteringoffice="
				+ isregisteringoffice + ", registeringofficecode=" + registeringofficecode + ", enabled=" + enabled
				+ "]";
	}

	
}
