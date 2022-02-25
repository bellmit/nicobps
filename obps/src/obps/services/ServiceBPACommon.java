package obps.services;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.util.application.BPAConstants;

import obps.util.application.ServiceUtilInterface;

@Service("BPAServiceCommon")
public class ServiceBPACommon {
	private static final Logger LOG = Logger.getLogger(ServiceBPACommon.class.toGenericString());
	@Autowired
	private ServiceUtilInterface SUI;

	public List<Map<String, Object>> listApplictionsCurrentProcessStatus(Integer USERCODE) {
		String sql = "SELECT AFR.applicationcode, BPA.edcrnumber, PF.flowname AS status,   "
				+ "      UL.fullname AS updatedby , TUL.fullname AS assignee,   "
				+ "      TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks,   "
				+ "      PR.processname processname, NPR.processname nextprocessname,   "
				+ "      PR.processname processname, CASE WHEN PF.processflowstatus = 'N' THEN NPR.processname ELSE PR.processname END nextprocessname,   "
				+ "		 TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate,  "
				+ "      RUL.fullname AS rejectedby, RA.remarks AS rejectremarks, Ra.entrusted, TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate ,TUL.fullname AS tousername  ,UL.fullname AS fromusername ,(plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address,EDCR.originalfilename,BPA.istatkal, "
				+ " TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate ,PR.processname AS currentprocessname ,OD.ownersname"
				+ " FROM nicobps.applicationflowremarks AFR     " + "INNER JOIN (  "
				+ "	SELECT applicationcode, MAX(entrydate)entrydate  "
				+ "	FROM nicobps.applicationflowremarks AFR     " + "	WHERE modulecode = AFR.modulecode  "
				+ "	GROUP BY applicationcode  "
				+ ")AF ON (AF.applicationcode, AF.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = AFR.applicationcode  "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.applicationcode = APP.applicationcode  "
				+ "INNER JOIN masters.processflow PF ON (PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (AFR.modulecode, AFR.fromprocesscode, AFR.toprocesscode) "
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.fromprocesscode AND AFR.modulecode = PR.modulecode    "
				+ "INNER JOIN masters.processes NPR ON NPR.processcode = PF.toprocesscode AND AFR.modulecode = NPR.modulecode    "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = AFR.fromusercode    " + "  INNER JOIN ( "
				+ "					SELECT BPA.applicationcode,  	ARRAY_TO_STRING(  	  ARRAY(  	   WITH data AS( "
				+ "						SELECT OD.applicationcode, OD.ownername,  		COUNT(1)                     "
				+ "						FROM nicobps.bpaownerdetails OD   		WHERE OD.applicationcode = BPA.applicationcode"
				+ "						GROUP BY 1, ownername   		ORDER BY applicationcode, ownername  	   ) "
				+ "						SELECT ownername AS businessitems  		FROM data 	  ),', ') AS ownersname"
				+ "					FROM nicobps.bpaapplications BPA  ) OD ON OD.applicationcode = AFR.applicationcode  "
				+ " INNER JOIN nicobps.edcrscrutiny EDCR  ON BPA.edcrnumber = EDCR.edcrnumber         "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AFR.tousercode    "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AFR.applicationcode  "
				+ "LEFT JOIN nicobps.userlogins RUL ON RUL.usercode = RA.usercode    " + "WHERE   "
				+ "	AFR.modulecode = ?   " + "	AND   " + "	APP.usercode = ? " + "ORDER BY APP.entrydate";
		return SUI.listGeneric(sql, new Object[] { BPAConstants.MODULE_CODE, USERCODE });
	}

	public List<Map<String, Object>> listAppScrutinyDetailsForBPAV2(Integer usercode) {
		String sql = "SELECT * FROM ( "
				+ " SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status as edcrstatus, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,EDCR.originalfilename,(plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address,         "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,      "
				+ "      AF.fromprocesscode, PRF.processname AS currentprocessname,AF.toprocesscode, PR.processname AS nextprocessname, PU.pageurl, PU.urlcode ,TUL.fullname AS tousername  ,FUL.fullname AS fromusername,BPA.istatkal , "
				+ " null AS status , null as  appdate,null as remarks, null AS rejectremarks,null as rejectdate,OD.ownersname "
				+ "FROM nicobps.edcrscrutiny EDCR         "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber         "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode       "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (      "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks      "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode        "
				+ "	GROUP BY applicationcode)      "
				+ " INNER JOIN nicobps.userlogins FUL ON FUL.usercode = AF.fromusercode "
				+ "  LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AF.tousercode     " + "  INNER JOIN ( "
				+ "					SELECT BPA.applicationcode,  	ARRAY_TO_STRING(  	  ARRAY(  	   WITH data AS( "
				+ "						SELECT OD.applicationcode, OD.ownername,  		COUNT(1)                     "
				+ "						FROM nicobps.bpaownerdetails OD   		WHERE OD.applicationcode = BPA.applicationcode"
				+ "						GROUP BY 1, ownername   		ORDER BY applicationcode, ownername  	   ) "
				+ "						SELECT ownername AS businessitems  		FROM data 	  ),', ') AS ownersname"
				+ "					FROM nicobps.bpaapplications BPA  ) OD ON OD.applicationcode = AF.applicationcode  "
				+ " INNER JOIN masters.processes PRF ON PRF.processcode = AF.fromprocesscode AND PRF.modulecode = AF.modulecode "
				+ " INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode       "
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode) = (APP.officecode, PR.modulecode, AF.fromprocesscode, PR.processcode)   "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       " + "INNER JOIN (    "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu     " + "	FROM nicobps.userpages UP    "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode     " + "	ORDER BY PU.urlcode    "
				+ ")UP ON UP.usercode = EDCR.usercode AND UP.urlcode = PU.urlcode     "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ?     " + "UNION ALL    "
				+ "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,EDCR.originalfilename ,    "
				+ "BPA.applicationcode, null AS officecode, null AS modulecode, null AS usercode, null AS applicationslno, null AS servicetypecode, null AS appdate ,      "
				+ "null AS fromprocesscode, null AS processname,null AS toprocesscode, null AS processname, null AS pageurl, null AS urlcode,null as plotaddressline1 ,null AS tousername ,null AS fromusername ,null as istatkal  ,    "
				+ " null AS status , null as  appdate,null as remarks, null AS rejectremarks,null as rejectdate,null as ownersname  "
				+ "FROM nicobps.edcrscrutiny EDCR         "
				+ "LEFT JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber         "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ? AND BPA.applicationcode IS NULL " + " ) as tabl "
				+ " " + " ORDER BY currentprocessname desc , istatkal DESC";
		return SUI.listGeneric(sql, new Object[] { usercode, usercode });
	}

	public List<Map<String, Object>> listBPApplications(Integer USERCODE) {
		String sql = "SELECT EDCR.originalfilename,TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,(plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address,EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status as edcrstatus, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,     "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,    "
				+ "      AF.toprocesscode,PRF.processname AS currentprocessname, PR.processname AS nextprocessname, AF.fromusercode, AF.tousercode, FUL.fullname AS fromusername, TUL.fullname AS tousername,  "
				+ "      PU.pageurl, PU.urlcode , BPA.istatkal , "
				+ " PF.flowname AS status , TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate,AF.remarks, RA.remarks AS rejectremarks,TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate,OD.ownersname "
				+ " FROM nicobps.edcrscrutiny EDCR       "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber   "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode     "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks    "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode      "
				+ "	GROUP BY applicationcode)    "
				+ "INNER JOIN nicobps.userlogins FUL ON FUL.usercode = AF.fromusercode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AF.tousercode  " + " INNER JOIN ("
				+ "				SELECT BPA.applicationcode,  	ARRAY_TO_STRING(  	  ARRAY(  	   WITH data AS("
				+ "				SELECT OD.applicationcode, OD.ownername,  		COUNT(1)                    "
				+ "				FROM nicobps.bpaownerdetails OD   		WHERE OD.applicationcode = BPA.applicationcode"
				+ "				GROUP BY 1, ownername   		ORDER BY applicationcode, ownername  	   )"
				+ "				SELECT ownername AS businessitems  		FROM data 	  ),', ') AS ownersname"
				+ "				FROM nicobps.bpaapplications BPA  ) OD ON OD.applicationcode = AF.applicationcode "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode   "
				+ "INNER JOIN masters.processes PRF ON PRF.processcode = AF.fromprocesscode AND PRF.modulecode = AF.modulecode  "
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode,  PF.processflowstatus) = (APP.officecode, PR.modulecode, AF.fromprocesscode, PR.processcode, 'N')        "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " + "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " + "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " + "	ORDER BY PU.urlcode  "
				+ ")UP ON CASE WHEN AF.tousercode IS NOT NULL "
				+ " AND UP.usercode NOT IN (SELECT usercode FROM nicobps.userlogins WHERE trim(designation) in ('Super User','GMC Super User')) "
				+ " THEN UP.usercode = AF.tousercode AND UP.urlcode = PU.urlcode   "
				+ "	ELSE UP.urlcode = PU.urlcode   " + "       END  "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AF.applicationcode  "
				+ " INNER JOIN nicobps.userofficelocations UOL ON   UP.usercode = UOL.usercode and BPA.officelocationcode=UOL.locationcode "
				+ "WHERE UP.usercode = ?  " + "AND RA.applicationcode IS NULL "
				+ " ORDER BY BPA.istatkal DESC,BPA.entrydate";
		return SUI.listGeneric(sql, new Object[] { USERCODE });
	}

	public List<Map<String, Object>> listBPApplications(Integer USERCODE, Integer processcode) {
		String sql = "SELECT EDCR.originalfilename,TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,(plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address,EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status as edcrstatus, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,       "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,    "
				+ "      AF.toprocesscode, PRF.processname AS currentprocessname, PR.processname AS nextprocessname, AF.fromusercode, AF.tousercode, FUL.fullname AS fromusername, TUL.fullname AS tousername,  "
				+ "      PU.pageurl, PU.urlcode  ,BPA.istatkal, "
				+ " PF.flowname AS status , TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate,AF.remarks, RA.remarks AS rejectremarks,TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate,OD.ownersname "
				+ "FROM nicobps.edcrscrutiny EDCR       "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber    "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode     "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks    "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode      "
				+ "	GROUP BY applicationcode)    "
				+ "INNER JOIN nicobps.userlogins FUL ON FUL.usercode = AF.fromusercode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AF.tousercode  " + " INNER JOIN ("
				+ "				SELECT BPA.applicationcode,  	ARRAY_TO_STRING(  	  ARRAY(  	   WITH data AS("
				+ "				SELECT OD.applicationcode, OD.ownername,  		COUNT(1)                    "
				+ "				FROM nicobps.bpaownerdetails OD   		WHERE OD.applicationcode = BPA.applicationcode"
				+ "				GROUP BY 1, ownername   		ORDER BY applicationcode, ownername  	   )"
				+ "				SELECT ownername AS businessitems  		FROM data 	  ),', ') AS ownersname"
				+ "				FROM nicobps.bpaapplications BPA  ) OD ON OD.applicationcode = AF.applicationcode "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode     "
				+ "INNER JOIN masters.processes PRF ON PRF.processcode = AF.fromprocesscode AND PRF.modulecode = AF.modulecode  "
				+ "INNER JOIN masters.processflow PF ON (PF.officecode, PF.modulecode, PF.fromprocesscode, PF.toprocesscode,  PF.processflowstatus) = (APP.officecode, PR.modulecode, AF.fromprocesscode, PR.processcode, 'N')        "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " + "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " + "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " + "	ORDER BY PU.urlcode  "
				+ ")UP ON CASE WHEN AF.tousercode IS NOT NULL "
				+ " AND UP.usercode NOT IN (SELECT usercode FROM nicobps.userlogins WHERE trim(designation) in ('Super User','GMC Super User')) "
				+ " THEN UP.usercode = AF.tousercode AND UP.urlcode = PU.urlcode   "
				+ "	ELSE UP.urlcode = PU.urlcode   " + "       END  "
				+ "LEFT JOIN nicobps.bparejectapplications RA ON RA.applicationcode = AF.applicationcode  "
				+ " INNER JOIN nicobps.userofficelocations UOL ON   UP.usercode = UOL.usercode and BPA.officelocationcode=UOL.locationcode "
				+ "WHERE UP.usercode = ?  " + "AND RA.applicationcode IS NULL AND AF.toprocesscode=? "
				+ " ORDER BY BPA.istatkal DESC, BPA.entrydate";
		return SUI.listGeneric(sql, new Object[] { USERCODE, processcode });
	}

	public List<Map<String, Object>> listBPApplicationsStatus(Integer usercode, String param) {
//		LOG.info("usercode: " + usercode + " param: " + param);
		String sql = "SELECT EDCR.originalfilename,TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,(plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address, AFR.applicationcode, BPA.edcrnumber, PF.flowname AS status,   "
				+ "      UL.fullname AS updatedby , TUL.fullname AS assignee,   "
				+ "      TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks,   "
				+ "      PR.processname currentprocessname, CASE WHEN PF.processflowstatus = 'N' THEN NPR.processname ELSE PR.processname END nextprocessname,   "
				+ "      OD.*, AA.permitnumber," + "      TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate, "
				+ "      RUL.fullname AS rejectedby, RA.remarks AS rejectremarks, Ra.entrusted, TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate , TUL.fullname AS tousername  ,UL.fullname AS fromusername ,OD.ownersname ,BPA.istatkal ,"
				+ "  TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate, RA.remarks AS rejectremarks,TO_CHAR(RA.entrydate,'DD/MM/YYYY') rejectdate "
				+ "FROM nicobps.applicationflowremarks AFR      INNER JOIN (  "
				+ "	SELECT applicationcode, MAX(entrydate)entrydate  "
				+ "	FROM nicobps.applicationflowremarks AFR  WHERE modulecode = AFR.modulecode  "
				+ "	GROUP BY applicationcode  "
				+ ")AF ON (AF.applicationcode, AF.entrydate) = (AFR.applicationcode, AFR.entrydate)  "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = AFR.applicationcode  "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.applicationcode = APP.applicationcode "
				+ "INNER JOIN nicobps.edcrscrutiny EDCR ON EDCR.edcrnumber = BPA.edcrnumber  "
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
				+ "LEFT JOIN nicobps.userlogins RUL ON RUL.usercode = RA.usercode     "
				+ " INNER JOIN nicobps.userofficelocations UOL on BPA.officelocationcode=UOL.locationcode  "
				+ "WHERE   " + "	AFR.modulecode = ?   AND UOL.usercode=?  " + " AND  APP.officecode = ("
				+ "		SELECT officecode FROM nicobps.useroffices WHERE usercode = ?" + "	)"
				+ "	AND (BPA.applicationcode = ? OR BPA.edcrnumber = ? 	"
				+ "		OR AA.permitnumber = ? OR LOWER(OD.ownersname) LIKE LOWER('%'||?||'%'))  "
				+ "ORDER BY APP.entrydate";
		return SUI.listGeneric(sql,
				new Object[] { BPAConstants.MODULE_CODE, usercode, usercode, param, param, param, param });

	}

}
