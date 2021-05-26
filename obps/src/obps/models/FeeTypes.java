package obps.models;

public class FeeTypes {
	private int feetypecode;
	private String feetypedescription;
	private String enabled;
	
		public FeeTypes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFeetypecode() {
		return feetypecode;
	}
	public void setFeetypecode(int feetypecode) {
		this.feetypecode = feetypecode;
	}
	public String getFeetypedescription() {
		return feetypedescription;
	}
	public void setFeetypedescription(String feetypedescription) {
		this.feetypedescription = feetypedescription;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return "FeeTypes [feetypecode=" + feetypecode + ", feetypedescription=" + feetypedescription + ", enabled="
				+ enabled + "]";
	}
}
