package obps.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaApplicationFee implements Serializable {
	private static final long serialVersionUID = 6L;

	private String applicationcode;
	private Integer feetypecode;
	private Integer feecode;
	private Integer paymentmode;
	private Double totalamount;

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public Integer getFeecode() {
		return feecode;
	}

	public void setFeecode(Integer feecode) {
		this.feecode = feecode;
	}

	public Integer getFeetypecode() {
		return feetypecode;
	}

	public void setFeetypecode(Integer feetypecode) {
		this.feetypecode = feetypecode;
	}

	public Integer getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Integer paymentmode) {
		this.paymentmode = paymentmode;
	}

	public Double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}

	@Override
	public String toString() {
		return "BpaApplicationFee [applicationcode=" + applicationcode + "]";
	}

}
