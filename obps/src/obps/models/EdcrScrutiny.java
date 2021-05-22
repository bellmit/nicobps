package obps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EdcrScrutiny {
	private Integer usercode;
	private Integer officecode;
	private String edcrnumber;
	@JsonIgnore
	private String planinfoobject;
	private String entrydate;
	private String status;
	private String scrutinyreport;
	private String dxffile;
	private String officename;
	
	public EdcrScrutiny() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getUsercode() {
		return usercode;
	}

	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}

	public Integer getOfficecode() {
		return officecode;
	}

	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}

	public String getEdcrnumber() {
		return edcrnumber;
	}

	public void setEdcrnumber(String edcrnumber) {
		this.edcrnumber = edcrnumber;
	}

	public String getPlaninfoobject() {
		return planinfoobject;
	}

	public void setPlaninfoobject(String planinfoobject) {
		this.planinfoobject = planinfoobject;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScrutinyreport() {
		return scrutinyreport;
	}

	public void setScrutinyreport(String scrutinyreport) {
		this.scrutinyreport = scrutinyreport;
	}

	public String getDxffile() {
		return dxffile;
	}

	public void setDxffile(String dxffile) {
		this.dxffile = dxffile;
	}

	public String getOfficename() {
		return officename;
	}

	public void setOfficename(String officename) {
		this.officename = officename;
	}

	public EdcrScrutiny(Integer usercode, Integer officecode, String edcrnumber, String planinfoobject,
			String entrydate, String status, String scrutinyreport, String dxffile, String officename) {
		super();
		this.usercode = usercode;
		this.officecode = officecode;
		this.edcrnumber = edcrnumber;
		this.planinfoobject = planinfoobject;
		this.entrydate = entrydate;
		this.status = status;
		this.scrutinyreport = scrutinyreport;
		this.dxffile = dxffile;
		this.officename = officename;
	}

	@Override
	public String toString() {
		return "EdcrScrutiny [usercode=" + usercode + ", officecode=" + officecode + ", edcrnumber=" + edcrnumber
				+ ", planinfoobject=" + planinfoobject + ", entrydate=" + entrydate + ", status=" + status
				+ ", scrutinyreport=" + scrutinyreport + ", dxffile=" + dxffile + ", officename=" + officename + "]";
	}



	
	
	
}
