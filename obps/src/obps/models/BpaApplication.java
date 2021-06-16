/*@author Decent Khongstia*/
package obps.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaApplication implements Serializable {
	private static final long serialVersionUID = 1L;
	private String applicationcode;
	private String edcrnumber;
	private Integer ownershiptypecode;
	private String ownershipsubtype;
	private String plotaddressline1;
	private String plotaddressline2;
	private String plotvillagetown;
	private Integer plotpincode;
	private String plotgiscoordinates;
	private Integer officelocationcode;
	private String landregistrationdetails;
	private String landregistrationno;
	private String plotidentifier1;
	private String plotidentifier2;
	private String plotidentifier3;
	private String holdingno;
	private String entrydate;
	private List<BpaOwnerDetail> ownerdetails;

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public String getEdcrnumber() {
		return edcrnumber;
	}

	public void setEdcrnumber(String edcrnumber) {
		this.edcrnumber = edcrnumber;
	}

	public Integer getOwnershiptypecode() {
		return ownershiptypecode;
	}

	public void setOwnershiptypecode(Integer ownershiptypecode) {
		this.ownershiptypecode = ownershiptypecode;
	}

	public String getOwnershipsubtype() {
		return ownershipsubtype;
	}

	public void setOwnershipsubtype(String ownershipsubtype) {
		this.ownershipsubtype = ownershipsubtype;
	}

	public String getPlotaddressline1() {
		return plotaddressline1;
	}

	public void setPlotaddressline1(String plotaddressline1) {
		this.plotaddressline1 = plotaddressline1;
	}

	public String getPlotaddressline2() {
		return plotaddressline2;
	}

	public void setPlotaddressline2(String plotaddressline2) {
		this.plotaddressline2 = plotaddressline2;
	}

	public String getPlotvillagetown() {
		return plotvillagetown;
	}

	public void setPlotvillagetown(String plotvillagetown) {
		this.plotvillagetown = plotvillagetown;
	}

	public Integer getPlotpincode() {
		return plotpincode;
	}

	public void setPlotpincode(Integer plotpincode) {
		this.plotpincode = plotpincode;
	}

	public String getPlotgiscoordinates() {
		return plotgiscoordinates;
	}

	public void setPlotgiscoordinates(String plotgiscoordinates) {
		this.plotgiscoordinates = plotgiscoordinates;
	}

	public Integer getOfficelocationcode() {
		return officelocationcode;
	}

	public void setOfficelocationcode(Integer officelocationcode) {
		this.officelocationcode = officelocationcode;
	}

	public String getLandregistrationdetails() {
		return landregistrationdetails;
	}

	public void setLandregistrationdetails(String landregistrationdetails) {
		this.landregistrationdetails = landregistrationdetails;
	}

	public String getLandregistrationno() {
		return landregistrationno;
	}

	public void setLandregistrationno(String landregistrationno) {
		this.landregistrationno = landregistrationno;
	}

	public String getPlotidentifier1() {
		return plotidentifier1;
	}

	public void setPlotidentifier1(String plotidentifier1) {
		this.plotidentifier1 = plotidentifier1;
	}

	public String getPlotidentifier2() {
		return plotidentifier2;
	}

	public void setPlotidentifier2(String plotidentifier2) {
		this.plotidentifier2 = plotidentifier2;
	}

	public String getPlotidentifier3() {
		return plotidentifier3;
	}

	public void setPlotidentifier3(String plotidentifier3) {
		this.plotidentifier3 = plotidentifier3;
	}

	public String getHoldingno() {
		return holdingno;
	}

	public void setHoldingno(String holdingno) {
		this.holdingno = holdingno;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}

	public List<BpaOwnerDetail> getOwnerdetails() {
		return ownerdetails;
	}

	public void setOwnerdetails(List<BpaOwnerDetail> ownerdetails) {
		this.ownerdetails = ownerdetails;
	}

	@Override
	public String toString() {
		return "BpaApplication [applicationcode=" + applicationcode + ", edcrnumber=" + edcrnumber
				+ ", ownershiptypecode=" + ownershiptypecode + ", ownershipsubtype=" + ownershipsubtype
				+ ", plotaddressline1=" + plotaddressline1 + ", plotaddressline2=" + plotaddressline2
				+ ", plotvillagetown=" + plotvillagetown + ", plotpincode="
				+ plotpincode + ", plotgiscoordinates=" + plotgiscoordinates + ", officelocationcode="
				+ officelocationcode + ", landregistrationdetails=" + landregistrationdetails + ", landregistrationno="
				+ landregistrationno + ", plotidentifier1=" + plotidentifier1 + ", plotidentifier2=" + plotidentifier2
				+ ", plotidentifier3=" + plotidentifier3 + ", holdingno=" + holdingno + ", entrydate=" + entrydate
				+ ", ownerdetails=" + ownerdetails + "]";
	}

}
