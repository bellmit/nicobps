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
	private String smsbody;
	private String emailbody;
	private String emailsubject;

	private String recipientMobileno;
	private String recipientEmailid;
	

	private String sms_url;
	private String email_api_hostnane;
	private String email_hostname;
	private String email_port;
	
	
	public Notification() {
		super();
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

	public String getSmsbody() {
		return smsbody;
	}

	public void setSmsbody(String smsbody) {
		this.smsbody = smsbody;
	}

	public String getEmailbody() {
		return emailbody;
	}

	public void setEmailbody(String emailbody) {
		this.emailbody = emailbody;
	}

	public String getEmailsubject() {
		return emailsubject;
	}

	public void setEmailsubject(String emailsubject) {
		this.emailsubject = emailsubject;
	}
	

	public String getSms_url() {
		return sms_url;
	}

	public void setSms_url(String sms_url) {
		this.sms_url = sms_url;
	}

	public String getEmail_api_hostnane() {
		return email_api_hostnane;
	}

	public void setEmail_api_hostnane(String email_api_hostnane) {
		this.email_api_hostnane = email_api_hostnane;
	}

	public String getEmail_hostname() {
		return email_hostname;
	}

	public void setEmail_hostname(String email_hostname) {
		this.email_hostname = email_hostname;
	}

	public String getEmail_port() {
		return email_port;
	}

	public void setEmail_port(String email_port) {
		this.email_port = email_port;
	}

	@Override
	public String toString() {
		return "Notification [officecode=" + officecode + ", officeshortname=" + officeshortname + ", smsusername="
				+ smsusername + ", smspassword=" + smspassword + ", smssenderid=" + smssenderid + ", securekey="
				+ securekey + ", senderemailid=" + senderemailid + ", emailidpassword=" + emailidpassword
				+ ", messageid=" + messageid + ", templateid=" + templateid + ", smsbody=" + smsbody + ", emailbody="
				+ emailbody + ", emailsubject=" + emailsubject + ", recipientMobileno=" + recipientMobileno
				+ ", recipientEmailid=" + recipientEmailid + ", sms_url=" + sms_url + ", email_api_hostnane="
				+ email_api_hostnane + ", email_hostname=" + email_hostname + ", email_port=" + email_port + "]";
	}


	
}
