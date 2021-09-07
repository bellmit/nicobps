/*@author Decent Khongstia*/
package obps.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import obps.daos.DaoBPAInterface;
import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaApproval;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.BPAConstants;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Service("BPAService")
class ServiceBPA implements ServiceBPAInterface {
	private static final Logger LOG = Logger.getLogger(ServiceBPA.class.toGenericString());
	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	private DaoBPAInterface DBI;

	public Integer getApplicationCurrentProcesscode(String applicationcode) {
		List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAConstants.MODULE_CODE, applicationcode);
		if (list != null) {
			Map<String, Object> map = list.get(0);
			if (map != null) {
				map.entrySet().forEach(System.out::println);
				return Integer.valueOf(map.get("fromprocesscode").toString());
			}
		}
		return null;
	}

	@Override
	public Integer getApplicationOfficecode(String applicationcode) {
		String sql = "SELECT officecode FROM nicobps.applications WHERE applicationcode = ?";
		return Integer.valueOf(SUI.getStringObject(sql, new Object[] { applicationcode }));
	}

	public Integer getApplicationUsercode(String applicationcode) {
		String sql = "SELECT usercode FROM nicobps.applications WHERE applicationcode = ?";
		return Integer.valueOf(SUI.getStringObject(sql, new Object[] { applicationcode }));
	}

	@Override
	public List<CommonMap> listDistricts(Integer statecode) {
		String sql = "SELECT districtcode AS key, statecode AS value1, districtname AS value  "
				+ "FROM masters.districts  " + "WHERE statecode = ?  " + "ORDER by districtname";
		return SUI.listCommonMap(sql, new Object[] { statecode });
	}

	@Override
	public List<CommonMap> listNextProcessingUsers(String applicationcode, Integer processcode) {
		Integer fromprocesscode = null, officecode = null;
		String sql = "SELECT UL.usercode AS key, UL.username  AS value " 
				+ "FROM nicobps.userlogins UL  "
				+ "INNER JOIN nicobps.userpages UP ON UP.usercode = UL.usercode  "
				+ "INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  "
				+ "INNER JOIN masters.processflow PF ON PF.urlcode = UP.urlcode  "
				+ "WHERE (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode, PF.processflowstatus ) "
				+ "	= (?,?,?,?,'N') " + "ORDER BY UL.username";
		officecode = getApplicationOfficecode(applicationcode);
		fromprocesscode = getApplicationCurrentProcesscode(applicationcode);
		return SUI.listCommonMap(sql,
				new Object[] { officecode, BPAConstants.MODULE_CODE, fromprocesscode, processcode });
	}

	@Override
	public List<CommonMap> listNextProcessingUsers(Integer usercode, String applicationcode) {
		String sql = "SELECT U.usercode AS key, U.fullname AS value  " + "FROM nicobps.applicationflowremarks AFR   "
				+ "INNER JOIN(  " + "	SELECT applicationcode, MAX(entrydate) entrydate  	  "
				+ "	FROM nicobps.applicationflowremarks    " + "	GROUP BY applicationcode  "
				+ ")T ON (T.applicationcode, T.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.processflowstatus) = (AFR.modulecode, AFR.toprocesscode, 'N')  "
				+ "INNER JOIN (  " + "	SELECT PU.urlcode, PU.pageurl,   "
				+ "	       UP.usercode, UL.username, UL.fullname  " + "	FROM masters.pageurls PU  "
				+ "	INNER JOIN nicobps.userpages UP ON UP.urlcode = PU.urlcode  "
				+ "	INNER JOIN nicobps.userlogins UL ON UL.usercode = UP.usercode  "
				+ "	ORDER BY UP.usercode, UP.urlcode  " + ")U ON U.urlcode = PF.urlcode  " + "LEFT JOIN (	"
				+ " SELECT usercode FROM nicobps.licensees  " + ") L ON L.usercode  = U.usercode  "
				+ "WHERE AFR.applicationcode = ?  " + "AND L.usercode IS NULL " + "ORDER BY U.fullname ";
		return SUI.listCommonMap(sql, new Object[] { applicationcode });
	}

	@Override
	public List<CommonMap> listOfficelocations() {
		String sql = "SELECT O.locationcode AS key, O.locationname AS value, nomenclature AS value1 "
				+ "FROM masters.officelocations O " + "ORDER BY O.locationname";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listOfficelocations(Integer officecode) {
		String sql = "SELECT O.locationcode AS key, O.locationname AS value, nomenclature AS value1 "
				+ "FROM masters.officelocations O  " + "WHERE O.officecode = ?  " + "ORDER BY O.locationname";
		return SUI.listCommonMap(sql, new Object[] { officecode });
	}

	@Override
	public List<CommonMap> listOwnershiptypes() {
		String sql = "SELECT OW.ownershiptypecode AS key, OW.ownershiptypename AS value  "
				+ "FROM masters.ownershiptypes OW  " + "ORDER BY OW.ownershiptypename";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listRelationshiptypes() {
		String sql = "SELECT RS.relationshiptypecode AS key, RS.relationshiptypename AS value  "
				+ "FROM masters.relationshiptypes RS " + "ORDER BY RS.relationshiptypename";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listSalutations() {
		String sql = "SELECT SL.salutationcode AS key, SL.salutationdescription AS value "
				+ "FROM masters.salutations SL " + "WHERE SL.enabled='Y' " + "ORDER BY SL.salutationdescription";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listStates() {
		String sql = "SELECT statecode AS key, statename AS value  " + "FROM masters.states  " + "ORDER BY statename";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<Map<String, Object>> listApplictionsCurrentProcessStatus(Integer USERCODE) {
		String sql = "SELECT AFR.applicationcode, BPA.edcrnumber, PF.flowname AS status,   "
				+ "      UL.fullname AS updatedby , TUL.fullname AS assignee,   "
				+ "      TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks,   "
				/* + "      PR.processname processname, NPR.processname nextprocessname,   " */
				+ "      PR.processname processname, CASE WHEN PF.processflowstatus = 'N' THEN NPR.processname ELSE PR.processname END nextprocessname,   "
				+ "		 TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate,  "
				+ "      RUL.fullname AS rejectedby, RA.remarks AS rejectremarks, Ra.entrusted, TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate  "
				+ "FROM nicobps.applicationflowremarks AFR     " + "INNER JOIN (  "
				+ "	SELECT applicationcode, MAX(entrydate)entrydate  "
				+ "	FROM nicobps.applicationflowremarks AFR     " + "	WHERE modulecode = AFR.modulecode  "
				+ "	GROUP BY applicationcode  "
				+ ")AF ON (AF.applicationcode, AF.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = AFR.applicationcode  "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.applicationcode = APP.applicationcode  "
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode) "
				/*
				 * +
				 * "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode) AND PF.processflowstatus = 'N'      "
				 */
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.fromprocesscode AND AFR.modulecode = PR.modulecode    "
				+ "INNER JOIN masters.processes NPR ON NPR.processcode = PF.toprocesscode AND AFR.modulecode = NPR.modulecode    "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = AFR.fromusercode    "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AFR.tousercode    "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.userlogins RUL ON RUL.usercode = RA.usercode    " + "WHERE   "
				+ "	AFR.modulecode = ?   " + "	AND   " + "	APP.usercode = ? " + "ORDER BY APP.entrydate";
		return SUI.listGeneric(sql, new Object[] { BPAConstants.MODULE_CODE, USERCODE });
	}

	@Override
	public List<Map<String, Object>> listAppScrutinyDetailsForBPA(Integer USERCODE) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,       "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,    "
				+ "      AF.toprocesscode, PR.processname, PU.pageurl, PU.urlcode     "
				+ "FROM nicobps.edcrscrutiny EDCR       "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber       "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode     "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks    "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode      "
				+ "	GROUP BY applicationcode)    "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode     "
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.toprocesscode) = (APP.officecode, PR.modulecode, PR.processcode) AND PF.processflowstatus = 'N'    "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " + "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " + "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " + "	ORDER BY PU.urlcode  "
				+ ")UP ON UP.usercode = EDCR.usercode AND UP.urlcode = PU.urlcode   "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ?  " + "UNION ALL  "
				+ "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,  "
				+ "BPA.applicationcode, null AS officecode, null AS modulecode, null AS usercode, null AS applicationslno, null AS servicetypecode, null AS appdate ,    "
				+ "null AS toprocesscode, null AS processname, null AS pageurl, null AS urlcode     "
				+ "FROM nicobps.edcrscrutiny EDCR       "
				+ "LEFT JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber       "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ? AND BPA.applicationcode IS NULL";
		return SUI.listGeneric(sql, new Object[] { USERCODE, USERCODE });
	}

	@Override
	public List<Map<String, Object>> listAppScrutinyDetailsForBPAV2(Integer usercode) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,         "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,      "
				+ "      AF.toprocesscode, PR.processname, PU.pageurl, PU.urlcode  "
				+ "FROM nicobps.edcrscrutiny EDCR         "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber         "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode       "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (      "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks      "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode        "
				+ "	GROUP BY applicationcode)      "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode       "
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (APP.officecode, PR.modulecode, AF.fromprocesscode, PR.processcode)   "
				/* + " AND PF.processflowstatus = 'N'      " */
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       " + "INNER JOIN (    "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu     " + "	FROM nicobps.userpages UP    "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode     " + "	ORDER BY PU.urlcode    "
				+ ")UP ON UP.usercode = EDCR.usercode AND UP.urlcode = PU.urlcode     "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ?     " + "UNION ALL    "
				+ "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,    "
				+ "BPA.applicationcode, null AS officecode, null AS modulecode, null AS usercode, null AS applicationslno, null AS servicetypecode, null AS appdate ,      "
				+ "null AS toprocesscode, null AS processname, null AS pageurl, null AS urlcode       "
				+ "FROM nicobps.edcrscrutiny EDCR         "
				+ "LEFT JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber         "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ? AND BPA.applicationcode IS NULL";
		return SUI.listGeneric(sql, new Object[] { usercode, usercode });
	}

	@Override
	public List<Map<String, Object>> listBPApplications(Integer USERCODE) {
		String sql = "SELECT EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,       "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,    "
				+ "      AF.toprocesscode, PR.processname, AF.fromusercode, AF.tousercode, FUL.fullname AS fromusername, TUL.fullname AS tousername,  "
				+ "      PU.pageurl, PU.urlcode    " + "FROM nicobps.edcrscrutiny EDCR       "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber       "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode     "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks    "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode      "
				+ "	GROUP BY applicationcode)    "
				+ "INNER JOIN nicobps.userlogins FUL ON FUL.usercode = AF.fromusercode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AF.tousercode  "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode     "
				/*
				 * +
				 * "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.toprocesscode,  PF.processflowstatus) = (APP.officecode, PR.modulecode, PR.processcode, 'N')    "
				 */
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode,  PF.processflowstatus) = (APP.officecode, PR.modulecode, AF.fromprocesscode, PR.processcode, 'N')        "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " + "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " + "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " + "	ORDER BY PU.urlcode  "
				+ ")UP ON CASE WHEN AF.tousercode IS NOT NULL THEN UP.usercode = AF.tousercode AND UP.urlcode = PU.urlcode   "
				+ "	ELSE UP.urlcode = PU.urlcode   " + "       END  "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AF.applicationcode  "
				+ "WHERE UP.usercode = ?  " + "AND RA.applicationcode IS NULL";
		return SUI.listGeneric(sql, new Object[] { USERCODE });
	}

	@Override
	public List<Map<String, Object>> listBPApplicationsStatus(Integer usercode, String param) {
		LOG.info("usercode: " + usercode + " param: " + param);
		String sql = "SELECT AFR.applicationcode, BPA.edcrnumber, PF.flowname AS status,   "
				+ "      UL.fullname AS updatedby , TUL.fullname AS assignee,   "
				+ "      TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks,   "
				+ "      PR.processname processname, CASE WHEN PF.processflowstatus = 'N' THEN NPR.processname ELSE PR.processname END nextprocessname,   "
				+ "      OD.*, AA.permitnumber," + "      TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate, "
				+ "      RUL.fullname AS rejectedby, RA.remarks AS rejectremarks, Ra.entrusted, TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate  "
				+ "FROM nicobps.applicationflowremarks AFR      INNER JOIN (  "
				+ "	SELECT applicationcode, MAX(entrydate)entrydate  "
				+ "	FROM nicobps.applicationflowremarks AFR  WHERE modulecode = AFR.modulecode  "
				+ "	GROUP BY applicationcode  "
				+ ")AF ON (AF.applicationcode, AF.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = AFR.applicationcode  "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.applicationcode = APP.applicationcode "
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode) "
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.fromprocesscode AND AFR.modulecode = PR.modulecode    "
				+ "INNER JOIN masters.processes NPR ON NPR.processcode = PF.toprocesscode AND AFR.modulecode = NPR.modulecode    "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = AFR.fromusercode    " + "INNER JOIN ( "
				+ "	SELECT BPA.applicationcode, " + "	ARRAY_TO_STRING( " + "	  ARRAY( " + "	   WITH data AS( "
				+ "		SELECT OD.applicationcode, OD.ownername, " + "		COUNT(1)                     "
				+ "		FROM nicobps.bpaownerdetails OD  " + "		WHERE OD.applicationcode = BPA.applicationcode"
				+ "		GROUP BY 1, ownername  " + "		ORDER BY applicationcode, ownername " + "	   ) "
				+ "		SELECT ownername AS businessitems " + "		FROM data" + "	  ),', ') AS ownersname"
				+ "	FROM nicobps.bpaapplications BPA " + ") OD ON OD.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AFR.tousercode    "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.bpaapproveapplications AA ON AA.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.userlogins RUL ON RUL.usercode = RA.usercode     " + "WHERE   "
				+ "	AFR.modulecode = ?  AND  APP.officecode = ("
				+ "		SELECT officecode FROM nicobps.useroffices WHERE usercode = ?" + "	)"
				+ "	AND (BPA.applicationcode = ? OR BPA.edcrnumber = ? 	"
				+ "		OR AA.permitnumber = ? OR LOWER(OD.ownersname) LIKE LOWER('%'||?||'%'))  "
				+ "ORDER BY APP.entrydate";
		return SUI.listGeneric(sql, new Object[] { BPAConstants.MODULE_CODE, usercode, param, param, param, param });
	}

	@Override
	public List<Map<String, Object>> listBPAConditions(String applicationcode) {
		String sql = "SELECT C.conditioncode, C.conditiondescription, OC.checked   " + "FROM masters.conditions C  "
				+ "INNER JOIN masters.officebpaconditions OC ON OC.conditioncode = C.conditioncode  "
				+ "WHERE OC.enabled = 'Y'  " + "AND OC.officecode = ?";
		Integer officecode = getApplicationOfficecode(applicationcode);
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { officecode });
		if (list != null && !list.isEmpty())
			return list;
		else
			return new ArrayList<>();
	}

	@Override
	public List<Map<String, Object>> listBPAEnclosures(String applicationcode) {
		String sql = "SELECT E.enclosurecode, E.enclosurename, E.enclosuredescription, ME.mandatory, ME.slno "
				+ "FROM masters.enclosures E "
				+ "INNER JOIN masters.modulesenclosures ME ON ME.enclosurecode = E.enclosurecode "
				+ "WHERE E.enabled = 'Y' " + "AND ME.officecode = ? " + "AND ME.modulecode = ? "
				+ "AND ME.processcode = ? " + "ORDER BY ME.sequenceno";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { getApplicationOfficecode(applicationcode),
				BPAConstants.MODULE_CODE, getApplicationCurrentProcesscode(applicationcode) });
		if (list != null && !list.isEmpty())
			return list;
		else
			return new ArrayList<>();
	}

	@Override
	public List<Map<String, Object>> listNextProcess(String applicationcode) {
		List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAConstants.MODULE_CODE, applicationcode);
		if (list != null) {
			Map<String, Object> map = list.get(0);
			if (map != null) {
				return SUI.getNextProcessflow(BPAConstants.MODULE_CODE,
						Integer.valueOf(map.get("fromprocesscode").toString()));
				/*
				 * return SUI.getAllNextProcessflows(BPAConstants.MODULE_CODE,
				 * Integer.valueOf(map.get("fromprocesscode").toString()));
				 */
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> listOfficePaymentMode(String applicationcode) {
		String sql = "SELECT PM.paymentmodecode, PM.paymentmodedescription, PM.mode   "
				+ "FROM masters.paymentmodes PM   "
				+ "INNER JOIN masters.officespaymentmodes OPM ON OPM.paymentmodecode = PM.paymentmodecode   "
				+ "WHERE OPM.officecode = ?";
		return SUI.listGeneric(sql, new Object[] { getApplicationOfficecode(applicationcode) });
	}

	@Override
	public List<Map<String, Object>> listRejectedApplications(Integer USERCODE) {
		String sql = "SELECT RA.slno, RA.applicationcode, RA.remarks, RA.entrusted, TO_CHAR(RA.entrydate, 'DD/MM/YYYY') rejectdate,  "
				+ "       UL.fullname as username, TUL.usercode   " + "FROM nicobps.bparejectapplications RA  "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.applicationcode = RA.applicationcode  "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = RA.usercode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = RA.usercode AND TUL.usercode = ?  ";

		return SUI.listGeneric(sql, new Object[] { USERCODE });
	}

	@Override
	public List<Map<String, Object>> listSiteInspectionQuestionnaires(String applicationcode) {
		String sql = "SELECT OQ.officecode, Q.questioncode, Q.questiondescription,  "
				+ "AQ.aqcode, AQ.response, AQ.remarks  " + "FROM masters.officequestionaires  OQ    "
				+ "INNER JOIN masters.questionaires Q ON Q.questioncode = OQ.questioncode    "
				+ "LEFT JOIN nicobps.applicationsquestionaires AQ ON AQ.questioncode = OQ.questioncode AND AQ.applicationcode = ?   "
				+ "WHERE UPPER(enabled) = 'Y'     " + "AND OQ.officecode = ?";
		return SUI.listGeneric(sql, new Object[] { applicationcode, getApplicationOfficecode(applicationcode) });
	}

	@Override
	public Map<String, Object> getBpaApplicationDetails(String applicationcode) {
		Map<String, Object> application = new HashMap<String, Object>();
		List<Map<String, Object>> applications = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> details = new ArrayList<Map<String, Object>>();
		String sql = "SELECT BPA.applicationcode, BPA.edcrnumber, BPA.ownershiptypecode, BPA.ownershipsubtype,   "
				+ "       BPA.plotaddressline1, BPA.plotaddressline2, BPA.plotvillagetown, BPA.plotpincode,   "
				+ "       BPA.plotgiscoordinates, BPA.officelocationcode, BPA.landregistrationdetails,   "
				+ "       BPA.landregistrationno, BPA.plotidentifier1, BPA.plotidentifier2, BPA.plotidentifier3,   "
				+ "       BPA.holdingno, BPA.entrydate," + "		  OL.locationname, OW.ownershiptypename    "
				+ "FROM nicobps.bpaapplications  BPA "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode  "
				+ "INNER JOIN masters.officelocations OL ON OL.locationcode = BPA.officelocationcode  "
				+ "INNER JOIN masters.ownershiptypes OW ON OW.ownershiptypecode = BPA.ownershiptypecode   "
				+ "WHERE BPA.applicationcode = ? ";
		applications = SUI.listGeneric(sql, new Object[] { applicationcode });

		if (applications != null && !applications.isEmpty()) {
			application = applications.get(0);
			sql = "SELECT O.ownerdetailcode, O.applicationcode, O.salutationcode, O.ownername,     "
					+ "       O.relationshiptypecode, O.relationname, O.mobileno, O.emailid, O.address,     "
					+ "       TO_CHAR(O.entrydate, 'DD/MM/YYYY') entrydate,  " + "       R.relationshiptypename  "
					+ "FROM nicobps.bpaownerdetails O    "
					+ "INNER JOIN masters.relationshiptypes R ON R.relationshiptypecode = O.relationshiptypecode  "
					+ "WHERE applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] { applicationcode });
			if (details != null && !details.isEmpty())
				application.put("ownerdetails", details);

			details = new ArrayList<Map<String, Object>>();
			sql = "SELECT BE.appenclosurecode, EN.enclosurename, EN.enclosuredescription, BE.enclosureimage,       "
					+ "       P.processcode, P.processname,    "
					+ "       TO_CHAR(BE.entrydate,'DD/MM/YYYY') uploaddate    " + "FROM nicobps.bpaenclosures BE      "
					+ "INNER JOIN masters.enclosures EN ON EN.enclosurecode = BE.enclosurecode      "
					+ "INNER JOIN masters.modulesenclosures ME ON ME.enclosurecode = EN.enclosurecode    "
					+ "INNER JOIN masters.processes P ON (P.processcode, P.modulecode) = (ME.processcode, ME.modulecode)    "
					+ "WHERE " + "    ME.officecode = ?    " + "AND ME.modulecode = ?    "
					+ "AND BE.applicationcode = ?    ";
			details = SUI.listGeneric(sql, new Object[] { getApplicationOfficecode(applicationcode),
					BPAConstants.MODULE_CODE, applicationcode });
			if (details != null && !details.isEmpty())
				application.put("documentdetails", details);
		}

		return application;
	}

	@Override
	public Map<String, Object> getBpaApplicationDetails(Integer USERCODE, String applicationcode) {
		Map<String, Object> application = new HashMap<String, Object>();
		List<Map<String, Object>> applications = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> details = new ArrayList<Map<String, Object>>();
		String sql = "SELECT BPA.applicationcode, BPA.edcrnumber, BPA.ownershiptypecode, BPA.ownershipsubtype,   "
				+ "       BPA.plotaddressline1, BPA.plotaddressline2, BPA.plotvillagetown, BPA.plotpincode,   "
				+ "       BPA.plotgiscoordinates, BPA.officelocationcode, BPA.landregistrationdetails,   "
				+ "       BPA.landregistrationno, BPA.plotidentifier1, BPA.plotidentifier2, BPA.plotidentifier3,   "
				+ "       BPA.holdingno, BPA.entrydate," + "		  OL.locationname  "
				+ "FROM nicobps.bpaapplications  BPA "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode  "
				+ "INNER JOIN masters.officelocations OL ON OL.locationcode = BPA.officelocationcode  "
				+ "WHERE BPA.applicationcode = ? AND APP.usercode = ? ";
		applications = SUI.listGeneric(sql, new Object[] { applicationcode, USERCODE });

		if (applications != null && !applications.isEmpty()) {
			application = applications.get(0);
			sql = "SELECT ownerdetailcode, applicationcode, salutationcode, ownername,   "
					+ "       relationshiptypecode, relationname, mobileno, emailid, address,   " + "       entrydate  "
					+ "FROM nicobps.bpaownerdetails  " + "WHERE applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] { applicationcode });
			if (details != null && !details.isEmpty())
				application.put("ownerdetails", details);

			details = new ArrayList<Map<String, Object>>();
			sql = "SELECT BE.appenclosurecode, BE.applicationcode, BE.enclosurecode, BE.enclosureimage,   "
					+ "       TO_CHAR(BE.entrydate,'DD/MM/YYYY') uploaddate,   "
					+ "       EN.enclosurename, EN.enclosuredescription  " + "FROM nicobps.bpaenclosures BE  "
					+ "INNER JOIN masters.enclosures EN ON EN.enclosurecode = BE.enclosurecode  "
					+ "WHERE BE.applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] { applicationcode });
			if (details != null && !details.isEmpty())
				application.put("documentdetails", details);
		}

		return application;
	}

	@Override
	public Map<String, Object> getBPAFee(Integer USERCODE, String applicationcode, Integer feetypecode) {
		Map<String, Object> response = new HashMap<>();

		String sql = "SELECT F.*, FT.feetypedescription  " + "FROM masters.feemaster F "
				+ "INNER JOIN masters.feetypes FT ON FT.feetypecode = F.feetypecode "
				+ "WHERE F.officecode=? and F.feetypecode=?";
		List<Map<String, Object>> list = SUI.listGeneric(sql,
				new Object[] { getApplicationOfficecode(applicationcode), feetypecode });
		if (list != null && !list.isEmpty()) {
			response = list.get(0);
			JSONObject json = new JSONObject(list.get(0));
			if (json.containsKey("feejson") && json.get("feejson") != null) {
				Map<String, Object> edcrObject = getEdcrDetailsV2(USERCODE, applicationcode);
				List<Map<String, Object>> fees = new ArrayList<>();

				JSONObject edcrJson = new JSONObject(), fee = new JSONObject();
				try {
					JSONParser parser = new JSONParser();
					edcrJson = (JSONObject) parser.parse((String) edcrObject.get("planinfoobject"));
					DocumentContext edcrContext = JsonPath.parse(edcrJson.toString());

					PGobject pgo = new PGobject();
					pgo = (PGobject) json.get("feejson");
					fee = (JSONObject) parser.parse(pgo.toString());
					JSONArray feeTypes = (JSONArray) fee.get("feeTypes");
					if (feeTypes != null && !feeTypes.isEmpty())
						calculateFeeType(feeTypes, edcrContext, fees, response);

					if (fees != null && !fees.isEmpty()) {
						response.remove("feejson");
						response.put("calculatedFee", fees);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return response;
		}
		return new HashMap<String, Object>();
	}

	private void calculateFeeType(JSONArray feeTypes, DocumentContext edcrContext, List<Map<String, Object>> fees,
			Map<String, Object> response) {
		Double amount = Double.valueOf(0), finalAmount = Double.valueOf(0);
		Map<String, Object> fee = new HashMap<>();
		String calcutionFeeType = "", calcutionFeeTypeName = "";
		net.minidev.json.JSONArray jsona = new net.minidev.json.JSONArray();
		for (Object feeType : feeTypes) {
			JSONObject calculationType = (JSONObject) feeType;
			DocumentContext calcContext = JsonPath.parse(calculationType.toString());

			calcutionFeeType = (String) calculationType.get("feeType");
			calcutionFeeTypeName = (String) calculationType.get("feeTypeName");
			amount = Double.valueOf(calculationType.get("amount").toString());

			fee = new HashMap<>();
			fee.put("code", calcutionFeeType);
			fee.put("name", calcutionFeeTypeName);
			if (calculationType.containsKey("calsiLogic")) {
				amount = Double.valueOf(0);
				if (calcutionFeeType.equals(BPAConstants.FEETYPE_FORM_SCRUTINIZATION_FEE)) {
					net.minidev.json.JSONArray blockTotalFloors = edcrContext
							.read("planDetail.blocks.*.building.totalFloors");
					Double plotArea = ((Double) edcrContext.read("planDetail.plot.area"));
					jsona = (net.minidev.json.JSONArray) calcContext.read("calsiLogic.*.tolerancelimit");
					Double plotAreaLimit = ((Double) jsona.get(0));
					Double lowerLimitValue = Double.valueOf(0), upperLimitValue = Double.valueOf(0);

					if (blockTotalFloors != null && !blockTotalFloors.isEmpty()) {
						int blockno = 0;
						List<Map<String, Object>> subFee = new ArrayList<>();
						for (Object obj : blockTotalFloors) {
							blockno++;
							Integer totalFloor = (Integer) obj;
							for (int i = 1; i <= totalFloor; i++) {
								Double amt = Double.valueOf(0);
								jsona = (net.minidev.json.JSONArray) calcContext
										.read("calsiLogic.*.floors." + i + ".lowerLimit");
								lowerLimitValue = (Double.valueOf(jsona.get(0).toString()));
								jsona = (net.minidev.json.JSONArray) calcContext
										.read("calsiLogic.*.floors." + i + ".upperLimit");
								upperLimitValue = (Double.valueOf(jsona.get(0).toString()));
								if (plotArea.compareTo(plotAreaLimit) <= 0) {
									amount += lowerLimitValue;
									amt = lowerLimitValue;
								} else {
									amount += upperLimitValue;
									amt = upperLimitValue;
								}
								fee = new HashMap<>();
								fee.put("name", String.format("Block %s Floor %s", blockno, i));
								fee.put("amount", amt);
								subFee.add(fee);
							}
						}
						finalAmount += amount;

						fee = new HashMap<>();
						fee.put("code", calcutionFeeType);
						fee.put("name", calcutionFeeTypeName);
						fee.put("amount", amount);
						fee.put("plotArea", plotArea);
						fee.put("details", subFee);
						fees.add(fee);
					}
				}
			} else {
				finalAmount += amount;
				fee.put("amount", amount);
				fees.add(fee);
			}
			response.remove("feeamount");
			response.put("feeamount", finalAmount);
		}
	}

	@Override
	public Map<String, Object> getCurrentProcessTaskStatus(Integer USERCODE, String applicationcode) {
		String sql = "SELECT AFR.applicationcode, PF.flowname AS status,   "
				+ "      UL.fullname AS updatedby , TUL.fullname AS assignee,   "
				+ "      TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks,   "
				+ "      PR.processname processname, NPR.processname nextprocessname,   "
				+ "      RUL.fullname AS rejectedby, RA.remarks AS rejectremarks, Ra.entrusted, TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate  "
				+ "FROM nicobps.applicationflowremarks AFR     " + "INNER JOIN (  "
				+ "	SELECT applicationcode, MAX(entrydate)entrydate  "
				+ "	FROM nicobps.applicationflowremarks AFR     " + "	WHERE modulecode = AFR.modulecode  "
				+ "	GROUP BY applicationcode  "
				+ ")AF ON (AF.applicationcode, AF.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				/*
				 * +
				 * "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode) AND PF.processflowstatus = 'N'      "
				 */
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode)   "
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.fromprocesscode AND AFR.modulecode = PR.modulecode    "
				+ "INNER JOIN masters.processes NPR ON NPR.processcode = PF.toprocesscode AND AFR.modulecode = NPR.modulecode    "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = AFR.fromusercode    "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AFR.tousercode    "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.userlogins RUL ON RUL.usercode = RA.usercode    "
				+ "WHERE AFR.modulecode = ? AND AFR.applicationcode = ?";
		List<Map<String, Object>> list = SUI.listGeneric(sql,
				new Object[] { BPAConstants.MODULE_CODE, applicationcode });
		if (list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}

	@Override
	public Map<String, Object> getEdcrDetails(Integer USERCODE, String edcrnumber) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " + "WHERE EDCR.usercode = ? AND EDCR.edcrnumber = ?";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { USERCODE, edcrnumber });
		if (list != null && !list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public Map<String, Object> getEdcrDetailsV2(String appcode) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " + "WHERE EDCR.edcrnumber = ( " + "	SELECT edcrnumber "
				+ "	FROM nicobps.bpaapplications " + "	WHERE applicationcode = ? " + ")";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { appcode });
		if (list != null && !list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public Map<String, Object> getEdcrDetailsV2(Integer USERCODE, String appcode) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " + "WHERE EDCR.usercode = ? AND EDCR.edcrnumber = ( "
				+ "	SELECT edcrnumber " + "	FROM nicobps.bpaapplications " + "	WHERE applicationcode = ? " + ")";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { USERCODE, appcode });
		if (list != null && !list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public boolean checkActionAccessGrantStatus(Integer USERCODE, String appcode) {
		String sql = "SELECT COALESCE(MAX(1), 0) AS accessflag        " + "FROM nicobps.applications APP        "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (     	    "
				+ "SELECT applicationcode, MAX(entrydate)   	     "
				+ "FROM nicobps.applicationflowremarks     	    " + "WHERE applicationcode = APP.applicationcode       "
				+ "AND modulecode = APP.modulecode       	    " + "GROUP BY applicationcode)         "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode          "
				+ "INNER JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode     "
				+ "AND PF.processflowstatus = 'N'         "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode         "
				+ "INNER JOIN nicobps.userpages UP ON (UP.urlcode, UP.usercode ) = (PU.urlcode, CASE WHEN AF.tousercode IS NOT NULL THEN AF.tousercode ELSE UP.usercode END)   "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AF.applicationcode     "
				+ "WHERE 1=1   " + "AND UP.usercode = ?  " + "AND AF.modulecode = ?       "
				+ "AND AF.applicationcode = ?     " + "AND RA.applicationcode IS NULL";
		return SUI.getStringObject(sql, new Object[] { USERCODE, BPAConstants.MODULE_CODE, appcode })
				.equals(String.valueOf(1));
	}

	@Override
	public boolean checkIfBuildingPermitAlreadyApplied(Integer USERCODE, String edcrnumber) {
		String sql = "SELECT COALESCE(MAX(1), 0) AS accessflag " + "FROM nicobps.bpaapplications "
				+ "WHERE edcrnumber = ?";
		return SUI.getStringObject(sql, new Object[] { edcrnumber }).equals(String.valueOf(1));
	}

	@Override
	public boolean checkPageAccessGrantStatus(Integer USERCODE, String appcode, String pathurl) {
		String sql = "SELECT COALESCE(MAX(1), 0) AS accessflag     " + "FROM nicobps.applications APP      "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (     	  "
				+ "SELECT applicationcode, MAX(entrydate)   	  " + "FROM nicobps.applicationflowremarks     	  "
				+ "WHERE applicationcode = APP.applicationcode    " + "AND modulecode = APP.modulecode       	  "
				+ "GROUP BY applicationcode)       "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode        "
				/*
				 * +
				 * "INNER JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode   "
				 * + "AND PF.processflowstatus = 'N'       "
				 */
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (PR.modulecode, AF.fromprocesscode, PR.processcode)  "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
				+ "INNER JOIN nicobps.userpages UP ON UP.urlcode =  PU.urlcode AND UP.usercode = ?     "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AF.applicationcode   "
				+ "WHERE 1=1 " + "AND AF.applicationcode = ?  " + "AND AF.modulecode = ?     "
				+ "AND CASE WHEN AF.tousercode IS NOT NULL THEN AF.tousercode = ? ELSE 1=1 END     "
				+ "AND PU.pageurl = ?  " + "AND RA.applicationcode IS NULL";
		pathurl = pathurl.replaceFirst("/", "");
		return SUI.getStringObject(sql, new Object[] { USERCODE, appcode, BPAConstants.MODULE_CODE, USERCODE, pathurl })
				.equals(String.valueOf(1));
	}

	/* CREATE */
	@Override
	public boolean approveBPApplication(BpaApproval bpa, HashMap<String, Object> response) {
		return DBI.approveBPApplication(bpa, response);
	}

	@Override
	public boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) {
		Map<String, Object> map = getBPAFee(USERCODE, bpa.getApplicationcode(), bpa.getFeetypecode());
		if (map != null) {
			bpa.setTotalamount(Double.valueOf(map.get("feeamount").toString()));
			bpa.setFeecode(Integer.valueOf(map.get("feecode").toString()));
		}
		return DBI.processAppPayment(USERCODE, bpa, response);
	}

	@Override
	public boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		if (data != null && data.getEnclosures() != null && !data.getEnclosures().isEmpty()) {
			data.getEnclosures().forEach(r -> {
				r.setFileImageByBase64String(r.getFile());
			});
		}
		return DBI.processBPApplication(data, response);
	}

	@Override
	public boolean rejectBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		return DBI.rejectBPApplication(data, response);
	}

	@Override
	public boolean returnFromCitizenBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		return DBI.returnFromCitizenBPApplication(data, response);
	}

	@Override
	public boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response) {
		return DBI.saveBPA(bpa, USERCODE, response);
	}

	@Override
	public boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response) {
		return DBI.saveBPAStepTwo(bpa, USERCODE, fromprocesscode, response);
	}

	@Override
	public boolean saveBPASiteInspection(BpaSiteInspection bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response) {
		if (bpa != null && bpa.getReports() != null && !bpa.getReports().isEmpty()) {
			bpa.getReports().forEach(r -> {
				r.setFileImageByBase64String(r.getFile());
			});
			return DBI.saveBPASiteInspection(bpa, USERCODE, fromprocesscode, response);
		} else {
			response.put("code", HttpStatus.BAD_REQUEST.value());
			response.put("msg", "Error: no site inspection reports uploaded.");
			return false;
		}
	}

	@Override
	public boolean sendToCitizenBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		Integer usercode = getApplicationUsercode(data.getApplicationcode());
		if (usercode != null) {
			data.setTousercode(usercode);
			return DBI.sendToCitizenBPApplication(data, response);
		} else
			return false;
	}

}
