package obps.models;

public class Filetypes {
	private int filetypecode;
	private String filetypedescription;
	private String enabled;
	
	
	
	public Filetypes() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getFiletypedescription() {
		return filetypedescription;
	}



	public void setFiletypedescription(String filetypedescription) {
		this.filetypedescription = filetypedescription;
	}



	public int getFiletypecode() {
		return filetypecode;
	}
	public void setFiletypecode(int filetypecode) {
		this.filetypecode = filetypecode;
	}

	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}



	@Override
	public String toString() {
		return "Filetypes [filetypecode=" + filetypecode + ", filetypedescription=" + filetypedescription + ", enabled="
				+ enabled + "]";
	}
	
	
	
}
