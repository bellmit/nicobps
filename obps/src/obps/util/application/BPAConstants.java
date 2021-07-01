package obps.util.application;

import java.util.logging.Logger;

public class BPAConstants {
	
	private static final Logger LOG = Logger.getLogger(BPAConstants.class.toGenericString());
	
	public static final Integer APPLICATION_FEE_CODE = 5;
	public static final Integer PERMIT_FEE_CODE = 6;
	public static final Integer MODULE_CODE = 2;
	
	public static final Integer FIRST_PROCESS_CODE = 1;
	public static final Integer NEXT_PROCESS_CODE = 2;
	
	public static final String COMPONENT_URL_MAPPING = "/bpa/components";

	public static final String FEETYPE_FORM_SCRUTINIZATION_FEE = "FormScrutinizationFee";
	public static final String FEETYPE_FORM_AND_EARTHQUAKE_FEE = "FormAndEarthquakeFee";
	public static final String FEETYPE_TDR_FEE = "TDRFee";
	public static final String FEETYPE_PERMIT_FEE = "PermitFee";
	
	
	public static final String PAGEURL_ROOT = "/";
	
	public static final String PAGEURL_PROCESS_DEFAULT = "process";
	
	public static final String PAGEURL_PROCESS_ADMINISTRATIVE_APPROVAL = "administrativeapproval";
	public static final String PAGEURL_PROCESS_APPROVAL_OF_SITEPLAN = "approvalofsiteplan";
	public static final String PAGEURL_PROCESS_CHECKING_OF_BPP = "checkingofbpp";
	public static final String PAGEURL_PROCESS_SCRUTINY_OF_BPP = "scrutinyofbpp";
	public static final String PAGEURL_PROCESS_SCRUTINY_OF_DOCUMENTS_AND_SITEPLAN = "scrutinyofdocumentsandsiteplan";
	public static final String PAGEURL_PROCESS_SITEINSPECTION = "siteinspection";
	public static final String PAGEURL_PROCESS_STRUCTURALCHECK = "structuralcheck";
	public static final String PAGEURL_PROCESS_TECHNICAL_APPROVAL = "technicalapproval";

	public static final String PARENT_URL_MAPPING = "/bpa";
	public static final String REDIRECT_MAPPING = "redirect:";
	
	public static final String SESSION_APPCODE = "SESSION_APPCODE";
	public static final String SESSION_PATHURL = "SESSION_PATHURL";
	public static final String SESSION_USERCODE = "SESSION_USERCODE";

	
	
	public static String getProcessPage(String pathurl) {
		LOG.info("pathurl: "+pathurl);
		pathurl = pathurl.replaceFirst("/bpa", "").replace(".htm", "");
		LOG.info("A: pathurl: "+pathurl);
		switch(pathurl) {
			case PAGEURL_PROCESS_ADMINISTRATIVE_APPROVAL:
				pathurl = PAGEURL_PROCESS_ADMINISTRATIVE_APPROVAL;
				break;
			case PAGEURL_PROCESS_APPROVAL_OF_SITEPLAN:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			case PAGEURL_PROCESS_CHECKING_OF_BPP:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			case PAGEURL_PROCESS_SCRUTINY_OF_BPP:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			case PAGEURL_PROCESS_SCRUTINY_OF_DOCUMENTS_AND_SITEPLAN:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			case PAGEURL_PROCESS_SITEINSPECTION:
				pathurl = PAGEURL_PROCESS_SITEINSPECTION;
				break;
			case PAGEURL_PROCESS_STRUCTURALCHECK:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			case PAGEURL_PROCESS_TECHNICAL_APPROVAL:
				pathurl = PAGEURL_PROCESS_DEFAULT;
				break;
			default:
				pathurl = PAGEURL_PROCESS_DEFAULT;
		}
		return PAGEURL_ROOT.concat(pathurl);
	}
}
