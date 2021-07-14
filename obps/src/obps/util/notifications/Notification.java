package obps.util.notifications;

public class Notification {
	
	private Integer officecode;
	private String officeshortname;

	private String smsusername;
	private String smspassword;
	private String smssenderid;
	private String securekey;

	private String emailid;
	private String emailidpassword;

	private String messageid;
	private String templateid;
	private String sms;
	private String email;

	public Notification() {
		super();
	}

	public Integer getOfficecode() {
		return officecode;
	}

	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}

	public String getOfficeshortname() {
		return officeshortname;
	}

	public void setOfficeshortname(String officeshortname) {
		this.officeshortname = officeshortname;
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

	public String getSecurekey() {
		return securekey;
	}

	public void setSecurekey(String securekey) {
		this.securekey = securekey;
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

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
