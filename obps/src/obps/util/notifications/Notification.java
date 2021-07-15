package obps.util.notifications;

public class Notification {

	private Integer officecode;
	private String officeshortname;

	private String smsusername;
	private String smspassword;
	private String smssenderid;
	private String securekey;
	private String senderemailid;
	private String emailidpassword;

	private String messageid;
	private String templateid;
	private String sms;
	private String email;

	private String recipientMobileno;
	private String recipientEmailid;
	private String emailsubject;

	public Notification() {
		super();
	}

	public Notification(Boolean flag) {
		super();
		this.smsusername = "";
		this.smspassword = "";
		this.smssenderid = "";
		this.securekey = "";
		this.messageid = "";
		this.templateid = "";
		this.sms = "";
		this.recipientMobileno = "";
		this.emailsubject="";
	}

	public String getSenderemailid() {
		return senderemailid;
	}

	public void setSenderemailid(String senderemailid) {
		this.senderemailid = senderemailid;
	}

	public String getRecipientMobileno() {
		return recipientMobileno;
	}

	public void setRecipientMobileno(String recipientMobileno) {
		this.recipientMobileno = recipientMobileno;
	}

	public String getRecipientEmailid() {
		return recipientEmailid;
	}

	public void setRecipientEmailid(String recipientEmailid) {
		this.recipientEmailid = recipientEmailid;
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

	public String getEmailsubject() {
		return emailsubject;
	}

	public void setEmailsubject(String emailsubject) {
		this.emailsubject = emailsubject;
	}

	@Override
	public String toString() {
		return "Notification [officecode=" + officecode + ", officeshortname=" + officeshortname + ", smsusername="
				+ smsusername + ", smspassword=" + smspassword + ", smssenderid=" + smssenderid + ", securekey="
				+ securekey + ", senderemailid=" + senderemailid + ", emailidpassword=" + emailidpassword
				+ ", messageid=" + messageid + ", templateid=" + templateid + ", sms=" + sms + ", email=" + email
				+ ", recipientMobileno=" + recipientMobileno + ", recipientEmailid=" + recipientEmailid 
				+ ", emailSubject=" + emailsubject + "]";
	}
	
}
