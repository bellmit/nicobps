package obps.models;

public class OfficePaymentModes {
	private Integer officecode;
	private Integer paymentmodecode;
	public OfficePaymentModes() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OfficePaymentModes(Integer officecode, Integer paymentmodecode) {
		super();
		this.officecode = officecode;
		this.paymentmodecode = paymentmodecode;
	}
	/**
	 * @return the officecode
	 */
	public Integer getOfficecode() {
		return officecode;
	}
	/**
	 * @param officecode the officecode to set
	 */
	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}
	/**
	 * @return the paymentmodecode
	 */
	public Integer getPaymentmodecode() {
		return paymentmodecode;
	}
	/**
	 * @param paymentmodecode the paymentmodecode to set
	 */
	public void setPaymentmodecode(Integer paymentmodecode) {
		this.paymentmodecode = paymentmodecode;
	}
	@Override
	public String toString() {
		return "OfficePaymentModes [officecode=" + officecode + ", paymentmodecode=" + paymentmodecode + "]";
	}
	
}
