package obps.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LicenseesRegistrationsm {
	private int licenseeregistrationcode;
	private String licenseedescription;
	private String enabled;
	
	
	public LicenseesRegistrationsm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getLicenseeregistrationcode() {
		return licenseeregistrationcode;
	}
	public void setLicenseeregistrationcode(int licenseeregistrationcode) {
		this.licenseeregistrationcode = licenseeregistrationcode;
	}
	public String getLicenseedescription() {
		return licenseedescription;
	}
	public void setLicenseedescription(String licenseedescription) {
		this.licenseedescription = licenseedescription;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	

	@Override
	public String toString() {
		return "LicenseesRegistrationsm [licenseeregistrationcode=" + licenseeregistrationcode
				+ ", licenseedescription=" + licenseedescription + ", enabled=" + enabled + "]";
	}
}
