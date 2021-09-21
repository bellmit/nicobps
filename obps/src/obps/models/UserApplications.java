package obps.models;

public class UserApplications 
{
	private Integer processcode;
	private String processname;	
	private Integer totalac;
		
	public UserApplications() {		
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

	public Integer getTotalac() {
		return totalac;
	}
	public void setTotalac(Integer totalac) {
		this.totalac = totalac;
	}	
	
}
