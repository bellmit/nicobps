package obps.models;

public class Usages {
	private String usagecode;
	private String suboccupancycode;
	private String usagename;
	private String description;
	private SubOccupancies suboccupancies;
	
	public Usages() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUsagecode() {
		return usagecode;
	}
	public void setUsagecode(String usagecode) {
		this.usagecode = usagecode;
	}
	public String getSuboccupancycode() {
		return suboccupancycode;
	}
	public void setSuboccupancycode(String suboccupancycode) {
		this.suboccupancycode = suboccupancycode;
	}
	public String getUsagename() {
		return usagename;
	}
	public void setUsagename(String usagename) {
		this.usagename = usagename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SubOccupancies getSuboccupancies() {
		return suboccupancies;
	}
	public void setSuboccupancies(SubOccupancies suboccupancies) {
		this.suboccupancies = suboccupancies;
	}
	@Override
	public String toString() {
		return "Usages [usagecode=" + usagecode + ", suboccupancycode=" + suboccupancycode + ", usagename=" + usagename
				+ ", description=" + description + ", suboccupancies=" + suboccupancies + "]";
	}
	
	
	
}
