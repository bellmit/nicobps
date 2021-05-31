package obps.models;

public class SubOccupancies {

	private String suboccupancycode;
	private String occupancycode;
	private String suboccupancyname;
	private String description;
	private Occupancies occupancies;
	
	
	public SubOccupancies() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getSuboccupancycode() {
		return suboccupancycode;
	}
	public void setSuboccupancycode(String suboccupancycode) {
		this.suboccupancycode = suboccupancycode;
	}
	public String getOccupancycode() {
		return occupancycode;
	}
	public void setOccupancycode(String occupancycode) {
		this.occupancycode = occupancycode;
	}
	public String getSuboccupancyname() {
		return suboccupancyname;
	}
	public void setSuboccupancyname(String suboccupancyname) {
		this.suboccupancyname = suboccupancyname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Occupancies getOccupancies() {
		return occupancies;
	}
	public void setOccupancies(Occupancies occupancies) {
		this.occupancies = occupancies;
	}
	
	@Override
	public String toString() {
		return "SubOccupancies [suboccupancycode=" + suboccupancycode + ", occupancycode=" + occupancycode
				+ ", suboccupancyname=" + suboccupancyname + ", description=" + description + ", occupancies="
				+ occupancies + "]";
	}
	
	
	
}
