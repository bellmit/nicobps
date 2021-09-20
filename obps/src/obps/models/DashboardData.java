package obps.models;

public class DashboardData 
{
	private String officename;
	private Integer totalac;
	private Integer approvedac;
	private Integer rejectedac;
	private Integer pendingac;
	
	
	public DashboardData() {		
	}	
	
	public String getOfficename() {
		return officename;
	}
	public void setOfficename(String officename) {
		this.officename = officename;
	}
	public Integer getTotalac() {
		return totalac;
	}
	public void setTotalac(Integer totalac) {
		this.totalac = totalac;
	}
	public Integer getApprovedac() {
		return approvedac;
	}
	public void setApprovedac(Integer approvedac) {
		this.approvedac = approvedac;
	}
	public Integer getRejectedac() {
		return rejectedac;
	}
	public void setRejectedac(Integer rejectedac) {
		this.rejectedac = rejectedac;
	}	
	public Integer getPendingac() {
		return pendingac;
	}
	public void setPendingac(Integer pendingac) {
		this.pendingac = pendingac;
	}
	
}
