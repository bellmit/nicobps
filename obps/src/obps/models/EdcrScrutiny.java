package obps.models;

public class EdcrScrutiny {
	private Integer usercode;
	private String edcrnumber;
	private String jsonresponse;
	private String log_date;
	
	
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
	public String getEdcrnumber() {
		return edcrnumber;
	}
	public void setEdcrnumber(String edcrnumber) {
		this.edcrnumber = edcrnumber;
	}
	public String getJsonresponse() {
		return jsonresponse;
	}
	public void setJsonresponse(String jsonresponse) {
		this.jsonresponse = jsonresponse;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public EdcrScrutiny(Integer usercode, String edcrnumber, String jsonresponse, String log_date) {
		super();
		this.usercode = usercode;
		this.edcrnumber = edcrnumber;
		this.jsonresponse = jsonresponse;
		this.log_date = log_date;
	}
	@Override
	public String toString() {
		return "EdcrScrutiny [usercode=" + usercode + ", edcrnumber=" + edcrnumber + ", jsonresponse=" + jsonresponse
				+ ", log_date=" + log_date + "]";
	}

	
	
}
