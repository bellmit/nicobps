package obps.models;

import java.util.List;

public class ModulesEnclosures {
	private Integer modulecode;
	private Integer enclosurecode;
	private Integer officecode;
	private Integer processcode;
	private Integer licenseetypecode;
	private Integer sequenceno;
	private List<Enclosures> mappedenclosures;
	
	
	public List<Enclosures> getMappedenclosures() {
		return mappedenclosures;
	}


	public void setMappedenclosures(List<Enclosures> mappedenclosures) {
		this.mappedenclosures = mappedenclosures;
	}


	public ModulesEnclosures() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getOfficecode() {
		return officecode;
	}


	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}


	public Integer getProcesscode() {
		return processcode;
	}


	public void setProcesscode(Integer processcode) {
		this.processcode = processcode;
	}


	public Integer getLicenseetypecode() {
		return licenseetypecode;
	}


	public void setLicenseetypecode(Integer licenseetypecode) {
		this.licenseetypecode = licenseetypecode;
	}


	public Integer getSequenceno() {
		return sequenceno;
	}


	public void setSequenceno(Integer sequenceno) {
		this.sequenceno = sequenceno;
	}


	public ModulesEnclosures(Integer modulecode, Integer enclosurecode) {
		super();
		this.modulecode = modulecode;
		this.enclosurecode = enclosurecode;
	}
	public Integer getModulecode() {
		return modulecode;
	}
	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}
	public Integer getEnclosurecode() {
		return enclosurecode;
	}
	public void setEnclosurecode(Integer enclosurecode) {
		this.enclosurecode = enclosurecode;
	}
	@Override
	public String toString() {
		return "ModulesEnclosures [modulecode=" + modulecode + ", enclosurecode=" + enclosurecode + "]";
	}
	
	
	
	
	
}