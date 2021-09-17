package obps.models;

public class Questionnaire {
	private int questioncode;
	private String questiondescription;
	private String enabled;
	
	
	
	public Questionnaire() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getQuestioncode() {
		return questioncode;
	}
	public void setQuestioncode(int questioncode) {
		this.questioncode = questioncode;
	}
	public String getQuestiondescription() {
		return questiondescription;
	}
	public void setQuestiondescription(String questiondescription) {
		this.questiondescription = questiondescription;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	@Override
	public String toString() {
		return "Questionnaire [questioncode=" + questioncode + ", questiondescription=" + questiondescription
				+ ", enabled=" + enabled + "]";
	}
	
	
	
}
