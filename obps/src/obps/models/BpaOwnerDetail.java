package obps.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaOwnerDetail implements Serializable {
	private static final long serialVersionUID = 2L;
	private Integer ownerdetailcode;
	private String applicationcode;
	private Integer salutationcode;
	private String ownername;
	private Integer relationshiptypecode;
	private String relationname;
	private String mobileno;
	private String emailid;
	private String address;
	private String entrydate;

	public Integer getOwnerdetailcode() {
		return ownerdetailcode;
	}

	public void setOwnerdetailcode(Integer ownerdetailcode) {
		this.ownerdetailcode = ownerdetailcode;
	}

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public Integer getSalutationcode() {
		return salutationcode;
	}

	public void setSalutationcode(Integer salutationcode) {
		this.salutationcode = salutationcode;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public Integer getRelationshiptypecode() {
		return relationshiptypecode;
	}

	public void setRelationshiptypecode(Integer relationshiptypecode) {
		this.relationshiptypecode = relationshiptypecode;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "BpaOwnerDetail [ownerdetailcode=" + ownerdetailcode + ", applicationcode=" + applicationcode
				+ ", salutationcode=" + salutationcode + ", ownername=" + ownername + ", relationshiptypecode="
				+ relationshiptypecode + ", relationname=" + relationname + ", mobileno=" + mobileno + ", emailid="
				+ emailid + ", address=" + address + ", entrydate=" + entrydate + "]";
	}

}
