/*@author Decent Khongstia*/
package obps.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaSiteInspection implements Serializable {
	private static final long serialVersionUID = 4L;

	private String applicationcode;
	private String report;
	private byte[] imageFile;
	
	private Integer tousercode;
	private String remarks;

	public String getApplicationcode() {
		return applicationcode;
	}

	public void setApplicationcode(String applicationcode) {
		this.applicationcode = applicationcode;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}

	public byte[] getImageFile() {
		return imageFile;
	}

	public void setImageFile(String report) {
		try {
			this.imageFile = Base64.getDecoder().decode(report.split(",")[1]);
		} catch (Exception e) {
			this.imageFile = null;
		}
	}

	public void setImageFile(byte[] imageFile) {
		this.imageFile = imageFile;
	}

	public Integer getTousercode() {
		return tousercode;
	}

	public void setTousercode(Integer tousercode) {
		this.tousercode = tousercode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "BpaSiteInspection [applicationcode=" + applicationcode + ", report=" + report + ", imageFile="
				+ Arrays.toString(imageFile) + ", tousercode=" + tousercode + ", remarks=" + remarks + "]";
	}

}
