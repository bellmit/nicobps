package obps.models;

import java.util.List;

public class OfficeLocations {
	private Integer officecode;
	private String officename1;
	private Integer locationcode;
	private String locationname;
	private String nomenclature;
	private String wardlocationlgdcode ;
	
	
	
	

	public String getWardlocationlgdcode() {
		return wardlocationlgdcode;
	}

	public void setWardlocationlgdcode(String wardlocationlgdcode) {
		this.wardlocationlgdcode = wardlocationlgdcode;
	}

	public Integer getWardlocationno() {
		return wardlocationno;
	}

	public void setWardlocationno(Integer wardlocationno) {
		this.wardlocationno = wardlocationno;
	}

	private Integer wardlocationno ;
	private List<UserOfficeLocations> mappedofficelocations;

	
	
	public OfficeLocations() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<UserOfficeLocations> getMappedofficelocations() {
		return mappedofficelocations;
	}

	public void setMappedofficelocations(List<UserOfficeLocations> mappedofficelocations) {
		this.mappedofficelocations = mappedofficelocations;
	}

	public Integer getOfficecode() {
		return officecode;
	}

	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}

	public Integer getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(Integer locationcode) {
		this.locationcode = locationcode;
	}

	public String getLocationname() {
		return locationname;
	}

	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}

	public String getNomenclature() {
		return nomenclature;
	}

	public void setNomenclature(String nomenclature) {
		this.nomenclature = nomenclature;
	}
	
	

	public String getOfficename1() {
		return officename1;
	}

	public void setOfficename1(String officename1) {
		this.officename1 = officename1;
	}

	@Override
	public String toString() {
		return "OfficeLocations [officecode=" + officecode + ", locationcode=" + locationcode + ", locationname="
				+ locationname + ", nomenclature=" + nomenclature + "]";
	}

}
