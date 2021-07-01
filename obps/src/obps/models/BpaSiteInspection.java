/*@author Decent Khongstia*/
package obps.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.jfree.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaSiteInspection implements Serializable {
	private static final long serialVersionUID = 4L;

	private String applicationcode;
	private String report;
	private List<BpaQuestionnaire> questionnaires;
	private List<String> reports;
	private byte[] imageFile;
	private List<byte[]> imageFiles;

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

	public List<BpaQuestionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<BpaQuestionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
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

	public List<byte[]> getImageFiles() {
		return imageFiles;
	}

	public void setImageFilesByBase64String(List<String> reports) {
		try {
			List<byte[]> files = new ArrayList<byte[]>();
			for(String r: reports) {
				files.add(Base64.getDecoder().decode(r.split(",")[1]));
			}
			this.imageFiles = files;
		} catch (Exception e) {
			this.imageFiles = null;
		}
		
	}

	public void setImageFiles(List<byte[]> imageFiles) {
		this.imageFiles = imageFiles;
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

	public String toStringAll() {
		return "BpaSiteInspection [applicationcode=" + applicationcode + ", report=" + report + ", questionnaires="
				+ questionnaires + ", reports=" + reports + ", imageFile=" + Arrays.toString(imageFile)
				+ ", imageFiles=" + imageFiles + ", tousercode=" + tousercode + ", remarks=" + remarks + "]";
	}
	
	@Override
	public String toString() {
		return "BpaSiteInspection [applicationcode=" + applicationcode + ", questionnaires="
				+ questionnaires + ", imageFile=" + Arrays.toString(imageFile)
				+ ", imageFiles=" + imageFiles + ", tousercode=" + tousercode + ", remarks=" + remarks + "]";
	}
}
