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
	private String preaddressline1;
	private String preaddressline2;
	private String pretownvillage;
	private Integer predistrictcode;
	private Integer prepincode;
	private String peraddressline1;
	private String peraddressline2;
	private String pertownvillage;
	private Integer perdistrictcode;
	private Integer perpincode;
	private String additionalinfo;
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

	public String getPreaddressline1() {
		return preaddressline1;
	}

	public void setPreaddressline1(String preaddressline1) {
		this.preaddressline1 = preaddressline1;
	}

	public String getPreaddressline2() {
		return preaddressline2;
	}

	public void setPreaddressline2(String preaddressline2) {
		this.preaddressline2 = preaddressline2;
	}

	public String getPretownvillage() {
		return pretownvillage;
	}

	public void setPretownvillage(String pretownvillage) {
		this.pretownvillage = pretownvillage;
	}

	public Integer getPredistrictcode() {
		return predistrictcode;
	}

	public void setPredistrictcode(Integer predistrictcode) {
		this.predistrictcode = predistrictcode;
	}

	public Integer getPrepincode() {
		return prepincode;
	}

	public void setPrepincode(Integer prepincode) {
		this.prepincode = prepincode;
	}

	public String getPeraddressline1() {
		return peraddressline1;
	}

	public void setPeraddressline1(String peraddressline1) {
		this.peraddressline1 = peraddressline1;
	}

	public String getPeraddressline2() {
		return peraddressline2;
	}

	public void setPeraddressline2(String peraddressline2) {
		this.peraddressline2 = peraddressline2;
	}

	public String getPertownvillage() {
		return pertownvillage;
	}

	public void setPertownvillage(String pertownvillage) {
		this.pertownvillage = pertownvillage;
	}

	public Integer getPerdistrictcode() {
		return perdistrictcode;
	}

	public void setPerdistrictcode(Integer perdistrictcode) {
		this.perdistrictcode = perdistrictcode;
	}

	public Integer getPerpincode() {
		return perpincode;
	}

	public void setPerpincode(Integer perpincode) {
		this.perpincode = perpincode;
	}

	public String getAdditionalinfo() {
		return additionalinfo;
	}

	public void setAdditionalinfo(String additionalinfo) {
		this.additionalinfo = additionalinfo;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	@Override
	public String toString() {
		return "BpaOwnerDetail [ownerdetailcode=" + ownerdetailcode + ", applicationcode=" + applicationcode
				+ ", salutationcode=" + salutationcode + ", ownername=" + ownername + ", relationshiptypecode="
				+ relationshiptypecode + ", relationname=" + relationname + ", mobileno=" + mobileno + ", emailid="
				+ emailid + ", address=" + address + ", preaddressline1=" + preaddressline1 + ", preaddressline2="
				+ preaddressline2 + ", pretownvillage=" + pretownvillage + ", predistrictcode=" + predistrictcode
				+ ", prepincode=" + prepincode + ", peraddressline1=" + peraddressline1 + ", peraddressline2="
				+ peraddressline2 + ", pertownvillage=" + pertownvillage + ", perdistrictcode=" + perdistrictcode
				+ ", perpincode=" + perpincode + ", additionalinfo=" + additionalinfo + ", entrydate=" + entrydate
				+ "]";
	}

}
