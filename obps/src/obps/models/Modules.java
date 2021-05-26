package obps.models;

import java.util.List;

public class Modules {
	private Integer modulecode;
	private String modulename;
	private List<Enclosures> mappedenclosures;
	public Modules() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Modules(Integer modulecode, String modulename) {
		super();
		this.modulecode = modulecode;
		this.modulename = modulename;
	}
	/**
	 * @return the modulecode
	 */
	public Integer getModulecode() {
		return modulecode;
	}
	/**
	 * @param modulecode the modulecode to set
	 */
	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}
	/**
	 * @return the modulename
	 */
	public String getModulename() {
		return modulename;
	}
	/**
	 * @param modulename the modulename to set
	 */
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	
	/**
	 * @return the mappedenclosures
	 */
	public List<Enclosures> getMappedenclosures() {
		return mappedenclosures;
	}
	/**
	 * @param mappedenclosures the mappedenclosures to set
	 */
	public void setMappedenclosures(List<Enclosures> mappedenclosures) {
		this.mappedenclosures = mappedenclosures;
	}
	@Override
	public String toString() {
		return "Modules [modulecode=" + modulecode + ", modulename=" + modulename + "]";
	}
	
	
	
	
	
}
