package obps.models;

public class Processes {

	private Integer processcode;
	private String processname;
	private Integer modulecode;

	public Processes() {
		super();
	}

	public Integer getProcesscode() {
		return processcode;
	}

	public void setProcesscode(Integer processcode) {
		this.processcode = processcode;
	}

	public String getProcessname() {
		return processname;
	}

	public void setProcessname(String processname) {
		this.processname = processname;
	}

	public Integer getModulecode() {
		return modulecode;
	}

	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}

}
