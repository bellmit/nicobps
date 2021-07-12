package obps.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import obps.util.application.CommonMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BpaApproval implements Serializable {
	private static final long serialVersionUID = 7L;

	private BpaProcessFlow processflow;
	private List<CommonMap> conditions;

	public BpaProcessFlow getProcessflow() {
		return processflow;
	}

	public void setProcessflow(BpaProcessFlow processflow) {
		this.processflow = processflow;
	}

	public List<CommonMap> getConditions() {
		return conditions;
	}

	public void setConditions(List<CommonMap> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return "BpaApproval [processflow=" + processflow + ", conditions=" + conditions + "]";
	}
}
