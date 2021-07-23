package obps.models;

import java.util.List;

public class UserOfficeLocations {
	private Integer usercode ;
	private Integer  locationcode ;
	private List<OfficeLocations> mappedofficelocations;
	
	
	
	
	
	public UserOfficeLocations() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "UserOfficeLocations [usercode=" + usercode + ", locationcode=" + locationcode + "]";
	}
	
	
	
	public List<OfficeLocations> getMappedofficelocations() {
		return mappedofficelocations;
	}
	public void setMappedofficelocations(List<OfficeLocations> mappedofficelocations) {
		this.mappedofficelocations = mappedofficelocations;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public Integer getLocationcode() {
		return locationcode;
	}
	public void setLocationcode(Integer locationcode) {
		this.locationcode = locationcode;
	}
	
	
}
