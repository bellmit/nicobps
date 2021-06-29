package obps.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.CommonMap;

public interface ServiceBPAInterface {
	
	Integer getApplicationOfficecode(String applicationcode); 
	
	List<CommonMap> listNextProcessingUsers(Integer usercode, String applicationcode);
	
	List<CommonMap> listOfficelocations();

	List<CommonMap> listOfficelocations(Integer officecode);

	List<CommonMap> listOwnershiptypes();

	List<CommonMap> listRelationshiptypes();

	List<CommonMap> listSalutations();

	List<Map<String, Object>> listApplictionsCurrentProcessStatus(Integer USERCODE);
	
	List<Map<String, Object>> listAppScrutinyDetailsForBPA(Integer USERCODE);

	List<Map<String, Object>> listBPApplications(Integer USERCODE);
	
	List<Map<String, Object>> listRejectedApplications(Integer USERCODE);
	
	List<Map<String, Object>> listNextProcess(String applicationcode);

	List<Map<String, Object>> listOfficePaymentMode(String applicationcode);

	List<Map<String, Object>> listSiteReportDetails(Integer USERCODE, String applicationcode);
	
	Map<String, Object> getBpaApplicationDetails(String applicationcode);

	Map<String, Object> getBpaApplicationDetails(Integer USERCODE, String applicationcode);
	
	Map<String, Object> getBPAFee(Integer uSERCODE, String applicationcode, Integer feetypecode);
	
//	Map<String, Object> getApplicationFee(Integer USERCODE, String applicationcode, Integer bpaApplicationfeeCode);

	Map<String, Object> getCurrentProcessTaskStatus(Integer USERCODE, String applicationcode);

	Map<String, Object> getEdcrDetails(Integer USERCODE, String edcrnumber);
	
	Map<String, Object> getEdcrDetailsV2(String appcode);
	
	Map<String, Object> getEdcrDetailsV2(Integer USERCODE, String appcode);
	
	/*
	 * Map<String, Object> getPermitFee(Integer USERCODE, String applicationcode,
	 * Integer bpaPermitfeeCode);
	 */

	boolean checkAccessGrantStatus(Integer USERCODE, String appcode, String pathurl);
	
	boolean checkIfBuildingPermitAlreadyApplied(Integer USERCODE, String edcrnumber);
	
	/* CREATE */
	boolean approveBPApplication(BpaProcessFlow data, HashMap<String, Object> response);
	
	boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response);

	boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response);

	boolean rejectBPApplication(BpaProcessFlow data, HashMap<String, Object> response);
	
	boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response);

	boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response);

	boolean saveBPASiteInspection(BpaSiteInspection bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response);

	
}
