package obps.models;

public class Occupancies {
	private String occupancycode;
	private String occupancyname;
	private String occupancyalias;
	
	
	
	public Occupancies() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOccupancycode() {
		return occupancycode;
	}
	public void setOccupancycode(String occupancycode) {
		this.occupancycode = occupancycode;
	}
	public String getOccupancyname() {
		return occupancyname;
	}
	public void setOccupancyname(String occupancyname) {
		this.occupancyname = occupancyname;
	}
	public String getOccupancyalias() {
		return occupancyalias;
	}
	public void setOccupancyalias(String occupancyalias) {
		this.occupancyalias = occupancyalias;
	}
	@Override
	public String toString() {
		return "Occupancies [occupancycode=" + occupancycode + ", occupancyname=" + occupancyname + ", occupancyalias="
				+ occupancyalias + "]";
	}
	
	
	
}
