package obps.models;

public class Enclosures {
	private Integer enclosurecode;
	private String enclosurename;
	private String enclosuredescription;
	private String filetypes;
	public Enclosures() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Enclosures(Integer enclosurecode, String enclosurename, String enclosuredescription,String filetypes) {
		super();
		this.enclosurecode = enclosurecode;
		this.enclosurename = enclosurename;
		this.enclosuredescription = enclosuredescription;
		this.filetypes = filetypes;
	}
	
	
	public String getFiletypes() {
		return filetypes;
	}
	public void setFiletypes(String filetypes) {
		this.filetypes = filetypes;
	}
	public Integer getEnclosurecode() {
		return enclosurecode;
	}
	public void setEnclosurecode(Integer enclosurecode) {
		this.enclosurecode = enclosurecode;
	}
	public String getEnclosurename() {
		return enclosurename;
	}
	public void setEnclosurename(String enclosurename) {
		this.enclosurename = enclosurename;
	}
	public String getEnclosuredescription() {
		return enclosuredescription;
	}
	public void setEnclosuredescription(String enclosuredescription) {
		this.enclosuredescription = enclosuredescription;
	}
	@Override
	public String toString() {
		return "Enclosures [enclosurecode=" + enclosurecode + ", enclosurename=" + enclosurename
				+ ", enclosuredescription=" + enclosuredescription + "]";
	}
	
	
	
}
