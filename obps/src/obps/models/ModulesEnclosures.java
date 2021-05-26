package obps.models;

public class ModulesEnclosures {
	private Integer modulecode;
	private Integer enclosurecode;
	public ModulesEnclosures() {
		super();
		// TODO Auto-generated constructor stub
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