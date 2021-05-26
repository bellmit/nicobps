package obps.models;

public class LicenseeTypes {
	private int licenseetypecode;
	private String licenseetypename;
	private String enabled;
	
	
	
	public int getLicenseetypecode() {
		return licenseetypecode;
	}
	public void setLicenseetypecode(int licenseetypecode) {
		this.licenseetypecode = licenseetypecode;
	}
	public String getLicenseetypename() {
		return licenseetypename;
	}
	public void setLicenseetypename(String licenseetypename) {
		this.licenseetypename = licenseetypename;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public LicenseeTypes() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "LicenseeTypes [licenseetypecode=" + licenseetypecode + ", licenseetypename=" + licenseetypename
				+ ", enabled=" + enabled + "]";
	}
	
	
	
}
