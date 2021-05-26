package obps.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FeeMaster {
	private Integer feecode;
	
	private Integer feeamount;
	
	private String calculated;
	private String enabled;
	private String entrydate;
	
	
	
	private Integer officecode;
	private Integer licenseetypecode;
	private Integer feetypecode;
	private String licenseetypename;
	private String feetypedescription;
	private String officename1;

	 private FeeTypes feetypes;
	 private Offices offices;
	 private LicenseeTypes licenseetypes;
	
	 
	 
	public FeeMaster() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public FeeTypes getFeetypes() {
		return feetypes;
	}


	public void setFeetypes(FeeTypes feetypes) {
		this.feetypes = feetypes;
	}


	public Offices getOffices() {
		return offices;
	}


	public void setOffices(Offices offices) {
		this.offices = offices;
	}


	public LicenseeTypes getLicenseetypes() {
		return licenseetypes;
	}


	public void setLicenseetypes(LicenseeTypes licenseetypes) {
		this.licenseetypes = licenseetypes;
	}


	public String getLicenseetypename() {
		return licenseetypename;
	}


	public void setLicenseetypename(String licenseetypename) {
		this.licenseetypename = licenseetypename;
	}


	public String getFeetypedescription() {
		return feetypedescription;
	}


	public void setFeetypedescription(String feetypedescription) {
		this.feetypedescription = feetypedescription;
	}


	public Integer getFeecode() {
		return feecode;
	}

	public void setFeecode(Integer feecode) {
		this.feecode = feecode;
	}

	public Integer getOfficecode() {
		return officecode;
	}

	public void setOfficecode(Integer officecode) {
		this.officecode = officecode;
	}

	public Integer getLicenseetypecode() {
		return licenseetypecode;
	}

	public void setLicenseetypecode(Integer licenseetypecode) {
		this.licenseetypecode = licenseetypecode;
	}

	public Integer getFeetypecode() {
		return feetypecode;
	}

	public void setFeetypecode(Integer feetypecode) {
		this.feetypecode = feetypecode;
	}

	public Integer getFeeamount() {
		return feeamount;
	}

	public void setFeeamount(Integer feeamount) {
		this.feeamount = feeamount;
	}

	public String getCalculated() {
		return calculated;
	}

	public void setCalculated(String calculated) {
		this.calculated = calculated;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getEntrydate() {
		return entrydate;
	}

	public void setEntrydate(String entrydate) {
		this.entrydate = entrydate;
	}


	@Override
	public String toString() {
		return "FeeMaster [feecode=" + feecode + ", feeamount=" + feeamount + ", calculated=" + calculated
				+ ", enabled=" + enabled + ", entrydate=" + entrydate + ", feetypes=" + feetypes + ", offices="
				+ offices + ", licenseetypes=" + licenseetypes + "]";
	}

	public String getOfficename1() {
		return officename1;
	}

	public void setOfficename1(String officename1) {
		this.officename1 = officename1;
	}

	

	
	
	
	
}
