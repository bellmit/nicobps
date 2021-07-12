package obps.models;

import java.util.ArrayList;
import java.util.List;

public class BpaEnclosures {

	private String usercode;
	private String sessioncaptcha;
	private String userresponsecaptcha;
	private String applicationcode;
	private String appenclosurecode;
	private String afrcode;
	private List<AppEnclosures> appenclosures = new ArrayList();
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getSessioncaptcha() {
		return sessioncaptcha;
	}
	public void setSessioncaptcha(String sessioncaptcha) {
		this.sessioncaptcha = sessioncaptcha;
	}
	public String getUserresponsecaptcha() {
		return userresponsecaptcha;
	}
	public void setUserresponsecaptcha(String userresponsecaptcha) {
		this.userresponsecaptcha = userresponsecaptcha;
	}
	public String getApplicationcode() {
		return applicationcode;
	}
	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}
	public String getAppenclosurecode() {
		return appenclosurecode;
	}
	public void setAppenclosurecode(String appenclosurecode) {
		this.appenclosurecode = appenclosurecode;
	}
	public String getAfrcode() {
		return afrcode;
	}
	public void setAfrcode(String afrcode) {
		this.afrcode = afrcode;
	}
	public List<AppEnclosures> getAppenclosures() {
		return appenclosures;
	}
	public void setAppenclosures(List<AppEnclosures> appenclosures) {
		this.appenclosures = appenclosures;
	}



}
