package obps.models;
import java.util.List;
import java.util.ArrayList;


public class LicensEesenclosures {
	private String usercode; 
    private String sessioncaptcha;    
    private String userresponsecaptcha; 	
	private String afrcode;
	private List<AppEnclosures> appenclosures = new ArrayList();
    

    public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	
	public String getAfrcode() {
		return afrcode;
	}
	public void setAfrcode(String afrcode) {
		this.afrcode = afrcode;
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
    
	public List<AppEnclosures> getAppenclosures() {
		return appenclosures;
	}
	public void setAppenclosures(List<AppEnclosures> appenclosures) {
		this.appenclosures = appenclosures;
	}    	
	
}
