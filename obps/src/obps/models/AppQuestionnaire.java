package obps.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppQuestionnaire implements Serializable {
	private static final long serialVersionUID = 6L;

	private Integer aqcode;
	private Integer modulecode;
	private Integer processcode;
	private Integer questioncode;
	private String remarks;
	private String response;

	public Integer getAqcode() {
		return aqcode;
	}

	public void setAqcode(Integer aqcode) {
		this.aqcode = aqcode;
	}

	public Integer getModulecode() {
		return modulecode;
	}

	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}

	public Integer getProcesscode() {
		return processcode;
	}

	public void setProcesscode(Integer processcode) {
		this.processcode = processcode;
	}

	public Integer getQuestioncode() {
		return questioncode;
	}

	public void setQuestioncode(Integer questioncode) {
		this.questioncode = questioncode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "BpaQuestionnaire [aqcode=" + aqcode + ", modulecode=" + modulecode + ", processcode=" + processcode
				+ ", questioncode=" + questioncode + ", remarks=" + remarks + ", response=" + response + "]";
	}

}
