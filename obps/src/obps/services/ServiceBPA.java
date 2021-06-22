/*@author Decent Khongstia*/
package obps.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoBPAInterface;
import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaOwnerDetail;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Service("BPAService")
class ServiceBPA implements ServiceBPAInterface {
	private static final Logger LOG = Logger.getLogger(ServiceBPA.class.toGenericString());
	private static final Integer BPAMODULECODE = 2;
	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	private DaoBPAInterface DBI;
	
	@Override
	public Integer getApplicationOfficecode(String applicationcode) {
		String sql = "SELECT officecode FROM nicobps.applications WHERE applicationcode = ?";
		return Integer.valueOf(SUI.getStringObject(sql, new Object[] {applicationcode}));
	}
	
	@Override
	public List<CommonMap> listOfficelocations() {
		String sql = "SELECT O.locationcode AS key, O.locationname AS value, nomenclature AS value1 "
				+ "FROM masters.officelocations O " 
				+ "ORDER BY O.locationname";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listOfficelocations(Integer officecode) {
		String sql = "SELECT O.locationcode AS key, O.locationname AS value, nomenclature AS value1 "
				+ "FROM masters.officelocations O  " 
				+ "WHERE O.officecode = ?  " 
				+ "ORDER BY O.locationname";
		return SUI.listCommonMap(sql, new Object[] { officecode });
	}

	@Override
	public List<CommonMap> listOwnershiptypes() {
		String sql = "SELECT OW.ownershiptypecode AS key, OW.ownershiptypename AS value  "
				+ "FROM masters.ownershiptypes OW  " 
				+ "ORDER BY OW.ownershiptypename";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listRelationshiptypes() {
		String sql = "SELECT RS.relationshiptypecode AS key, RS.relationshiptypename AS value  "
				+ "FROM masters.relationshiptypes RS " 
				+ "ORDER BY RS.relationshiptypename";
		return SUI.listCommonMap(sql);
	}

	@Override
	public List<CommonMap> listSalutations() {
		String sql = "SELECT SL.salutationcode AS key, SL.salutationdescription AS value "
				+ "FROM masters.salutations SL " 
				+ "WHERE SL.enabled='Y' "
				+ "ORDER BY SL.salutationdescription";
		return SUI.listCommonMap(sql);
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
				+ "INNER JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode AND PF.processflowstatus = 'N'    "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " 
				+ "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " 
				+ "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " 
				+ "	ORDER BY PU.urlcode  "
				+ ")UP ON UP.usercode = EDCR.usercode AND UP.urlcode = PU.urlcode   "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ?  " 
				+ "UNION ALL  "
				+ "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,  "
				+ "BPA.applicationcode, null AS officecode, null AS modulecode, null AS usercode, null AS applicationslno, null AS servicetypecode, null AS appdate ,    "
				+ "null AS toprocesscode, null AS processname, null AS pageurl, null AS urlcode     "
				+ "FROM nicobps.edcrscrutiny EDCR       "
				+ "LEFT JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber       "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ? AND BPA.applicationcode IS NULL";
		return SUI.listGeneric(sql, new Object[] { USERCODE, USERCODE });
	}
	
	

	@Override
	public List<Map<String, Object>> listBPApplications(Integer USERCODE) {
		String sql = "SELECT EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,       "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,    "
				+ "      AF.toprocesscode, PR.processname, AF.fromusercode, AF.tousercode, FUL.fullname AS fromusername, TUL.fullname AS tousername,  "
				+ "      PU.pageurl, PU.urlcode    " 
				+ "FROM nicobps.edcrscrutiny EDCR       "
				+ "INNER JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber       "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode     "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "
				+ "	SELECT applicationcode, MAX(entrydate)   	FROM nicobps.applicationflowremarks    "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode      "
				+ "	GROUP BY applicationcode)    "
				+ "INNER JOIN nicobps.userlogins FUL ON FUL.usercode = AF.fromusercode  "
				+ "LEFT JOIN nicobps.userlogins TUL ON TUL.usercode = AF.tousercode  "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode     "
				+ "INNER JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode AND PF.processflowstatus = 'N'    "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode    " 
				+ "INNER JOIN (  "
				+ "	SELECT UP.usercode, UP.urlcode, PU.pageurl, PU.submenu  " 
				+ "	FROM nicobps.userpages UP  "
				+ "	INNER JOIN masters.pageurls PU ON PU.urlcode = UP.urlcode  " 
				+ "	ORDER BY PU.urlcode  "
				+ ")UP ON CASE WHEN AF.tousercode IS NOT NULL THEN UP.usercode = AF.tousercode AND UP.urlcode = PU.urlcode   "
				+ "	ELSE UP.urlcode = PU.urlcode   " 
				+ "       END  " 
				+ "WHERE UP.usercode = ?";
		return SUI.listGeneric(sql, new Object[] {USERCODE});
	}

	@Override
	public List<Map<String, Object>> listSiteReportDetails(Integer USERCODE, String applicationcode) {
		String sql = "SELECT SI.appenclosurecode, SI.applicationcode, SI.enclosurecode, SI.enclosureimage,   "
				+ " TO_CHAR(SI.entrydate, 'DD/MM/YYYY') entrydate, E.enclosurename      " 
				+ "FROM nicobps.bpasiteinspectiondetails SI "
				+ "LEFT JOIN masters.enclosures E ON E.enclosurecode = SI.enclosurecode   " 
				+ "WHERE applicationcode = ?  ";
		return SUI.listGeneric(sql, new Object[] {applicationcode});
	}
	
	@Override
	public List<Map<String, Object>> listNextProcess(String applicationcode) {
		List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAMODULECODE, applicationcode);
		if(list != null) {
			Map<String, Object> map = list.get(0);
			if(map != null) {
				return SUI.getAllNextProcessflows(BPAMODULECODE, Integer.valueOf(map.get("fromprocesscode").toString()));
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
	public Map<String, Object> getBpaApplicationDetails(String applicationcode) {
		Map<String, Object> application = new HashMap<String, Object>();
		List<Map<String, Object>> applications = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
		String sql = "SELECT BPA.applicationcode, BPA.edcrnumber, BPA.ownershiptypecode, BPA.ownershipsubtype,   "
				+ "       BPA.plotaddressline1, BPA.plotaddressline2, BPA.plotvillagetown, BPA.plotpincode,   "
				+ "       BPA.plotgiscoordinates, BPA.officelocationcode, BPA.landregistrationdetails,   "
				+ "       BPA.landregistrationno, BPA.plotidentifier1, BPA.plotidentifier2, BPA.plotidentifier3,   "
				+ "       BPA.holdingno, BPA.entrydate,"
				+ "		  OL.locationname  " 
				+ "FROM nicobps.bpaapplications  BPA "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode  "
				+ "INNER JOIN masters.officelocations OL ON OL.locationcode = BPA.officelocationcode  " 
				+ "WHERE BPA.applicationcode = ? ";
		applications = SUI.listGeneric(sql, new Object[] {applicationcode});
		
		if(applications != null && !applications.isEmpty()) {
			application = applications.get(0);
			sql = "SELECT ownerdetailcode, applicationcode, salutationcode, ownername,   "
					+ "       relationshiptypecode, relationname, mobileno, emailid, address,   "
					+ "       entrydate  "
					+ "FROM nicobps.bpaownerdetails  " 
					+ "WHERE applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] {applicationcode});
			if(details  != null && !details .isEmpty())
				application.put("ownerdetails", details);
			
			details = new ArrayList<Map<String,Object>>();
			sql = "SELECT BE.appenclosurecode, BE.applicationcode, BE.enclosurecode, BE.enclosureimage,   "
					+ "       TO_CHAR(BE.entrydate,'DD/MM/YYYY') uploaddate,   "
					+ "       EN.enclosurename, EN.enclosuredescription  " 
					+ "FROM nicobps.bpaenclosures BE  "
					+ "INNER JOIN masters.enclosures EN ON EN.enclosurecode = BE.enclosurecode  "
					+ "WHERE BE.applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] {applicationcode});
			if(details  != null && !details .isEmpty())
				application.put("documentdetails", details);
		}
		
		return application;
	}
	
	@Override
	public Map<String, Object> getBpaApplicationDetails(Integer USERCODE, String applicationcode) {
		Map<String, Object> application = new HashMap<String, Object>();
		List<Map<String, Object>> applications = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> details = new ArrayList<Map<String,Object>>();
		String sql = "SELECT BPA.applicationcode, BPA.edcrnumber, BPA.ownershiptypecode, BPA.ownershipsubtype,   "
				+ "       BPA.plotaddressline1, BPA.plotaddressline2, BPA.plotvillagetown, BPA.plotpincode,   "
				+ "       BPA.plotgiscoordinates, BPA.officelocationcode, BPA.landregistrationdetails,   "
				+ "       BPA.landregistrationno, BPA.plotidentifier1, BPA.plotidentifier2, BPA.plotidentifier3,   "
				+ "       BPA.holdingno, BPA.entrydate,"
				+ "		  OL.locationname  " 
				+ "FROM nicobps.bpaapplications  BPA "
				+ "INNER JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode  "
				+ "INNER JOIN masters.officelocations OL ON OL.locationcode = BPA.officelocationcode  " 
				+ "WHERE BPA.applicationcode = ? AND APP.usercode = ? ";
		applications = SUI.listGeneric(sql, new Object[] {applicationcode, USERCODE});
		
		if(applications != null && !applications.isEmpty()) {
			application = applications.get(0);
			sql = "SELECT ownerdetailcode, applicationcode, salutationcode, ownername,   "
					+ "       relationshiptypecode, relationname, mobileno, emailid, address,   "
					+ "       entrydate  "
					+ "FROM nicobps.bpaownerdetails  " 
					+ "WHERE applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] {applicationcode});
			if(details  != null && !details .isEmpty())
				application.put("ownerdetails", details);
			
			details = new ArrayList<Map<String,Object>>();
			sql = "SELECT BE.appenclosurecode, BE.applicationcode, BE.enclosurecode, BE.enclosureimage,   "
					+ "       TO_CHAR(BE.entrydate,'DD/MM/YYYY') uploaddate,   "
					+ "       EN.enclosurename, EN.enclosuredescription  " 
					+ "FROM nicobps.bpaenclosures BE  "
					+ "INNER JOIN masters.enclosures EN ON EN.enclosurecode = BE.enclosurecode  "
					+ "WHERE BE.applicationcode = ?";
			details = SUI.listGeneric(sql, new Object[] {applicationcode});
			if(details  != null && !details .isEmpty())
				application.put("documentdetails", details);
		}
		
		return application;
	}

	@Override
	public Map<String, Object> getApplicationFee(Integer USERCODE, String applicationcode,
			Integer feetypecode) {
		String sql = "SELECT F.feecode, F.feeamount, F.officecode " + 
				"FROM masters.feemaster F " + 
				"WHERE officecode=? and feetypecode=?";
		List<Map<String, Object>> list = SUI.listGeneric(sql,
				new Object[] { getApplicationOfficecode(applicationcode), feetypecode });
		if(list != null)
			return list.get(0);
		return new HashMap<String, Object>();
	}

	@Override
	public Map<String, Object> getCurrentProcessTaskStatus(Integer USERCODE, String applicationcode) {
		String sql = "SELECT pf.*,pu.pageurl,pu.parent,pu.parenticon, UL.fullname, PR.processname, TO_CHAR(AFR.entrydate, 'DD/MM/YYYY') taskdate, AFR.remarks "
				+ "FROM nicobps.applicationflowremarks AFR   "
				+ "INNER JOIN masters.processflow PF on PF.fromprocesscode=AFR.toprocesscode and processflowstatus='N' AND PF.modulecode=AFR.modulecode   "
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.fromprocesscode AND AFR.modulecode = PR.modulecode  "
				+ "INNER JOIN nicobps.userlogins UL ON UL.usercode = AFR.fromusercode  "
				+ "LEFT JOIN masters.pageurls PU on PU.urlcode=PF.urlcode   "
				+ "WHERE afrcode=(SELECT max(afrcode) FROM nicobps.applicationflowremarks WHERE modulecode=? AND applicationcode=?::text) ";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] {BPAMODULECODE, applicationcode});
		if(list != null && !list.isEmpty())
			return list.get(0);
		return null;
	}

	@Override
	public Map<String, Object> getEdcrDetails(Integer USERCODE, String edcrnumber) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " 
				+ "WHERE EDCR.usercode = ? AND EDCR.edcrnumber = ?";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { USERCODE, edcrnumber });
		if (list != null && !list.isEmpty())
			return list.get(0);

		return null;
	}

	@Override
	public Map<String, Object> getEdcrDetailsV2(String appcode) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " 
				+ "WHERE EDCR.edcrnumber = ( "
				+ "	SELECT edcrnumber "
				+ "	FROM nicobps.bpaapplications "
				+ "	WHERE applicationcode = ? "
				+ ")";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { appcode });
		if (list != null && !list.isEmpty())
			return list.get(0);
		
		return null;
	}
	
	@Override
	public Map<String, Object> getEdcrDetailsV2(Integer USERCODE, String appcode) {
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, EDCR.entrydate   "
				+ "FROM nicobps.edcrscrutiny EDCR   " 
				+ "WHERE EDCR.usercode = ? AND EDCR.edcrnumber = ( "
				+ "	SELECT edcrnumber "
				+ "	FROM nicobps.bpaapplications "
				+ "	WHERE applicationcode = ? "
				+ ")";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { USERCODE, appcode });
		if (list != null && !list.isEmpty())
			return list.get(0);
		
		return null;
	}

	
	@Override
	public Map<String, Object> getPermitFee(Integer USERCODE, String applicationcode, 	
			Integer feetypecode) {
		String sql = "SELECT F.feecode, F.feeamount " + 
				"FROM masters.feemaster F " + 
				"WHERE officecode=? and feetypecode=?";
		List<Map<String, Object>> list = SUI.listGeneric(sql,
				new Object[] { getApplicationOfficecode(applicationcode), feetypecode });
		if(list != null)
			return list.get(0);
		return new HashMap<String, Object>();
	}


	/* CREATE */
	@Override
	public boolean checkAccessGrantStatus(Integer USERCODE, String appcode, String pathurl) {
		String sql = "SELECT COALESCE(MAX(1), 0) AS accessflag     " 
				+ "FROM nicobps.applications APP      "
				+ "INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (     	  "
				+ "SELECT applicationcode, MAX(entrydate)   	  " 
				+ "FROM nicobps.applicationflowremarks     	  "
				+ "WHERE applicationcode = APP.applicationcode    " 
				+ "AND modulecode = APP.modulecode       	  "
				+ "GROUP BY applicationcode)       "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode        "
				+ "INNER JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode   "
				+ "AND PF.processflowstatus = 'N'       "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
				+ "INNER JOIN nicobps.userpages UP ON UP.urlcode =  PU.urlcode AND UP.usercode = ?     "
				+ "WHERE 1=1 "
				+ "AND AF.applicationcode = ?  " 
				+ "AND AF.modulecode = ?     "
				+ "AND CASE WHEN AF.tousercode IS NOT NULL THEN AF.tousercode = ? ELSE 1=1 END     "
				+ "AND PU.pageurl = ?";
		pathurl = pathurl.replaceFirst("/", "");
		LOG.info("pathurl: "+pathurl);
		return SUI.getStringObject(sql, new Object[] {USERCODE, appcode, BPAMODULECODE, USERCODE, pathurl}).equals(String.valueOf(1));
	}

	@Override
	public boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) {
		Map<String, Object> map = getApplicationFee(USERCODE, bpa.getApplicationcode(), bpa.getFeetypecode());
		if(map != null) {
			bpa.setTotalamount(Double.valueOf(map.get("feeamount").toString()));
			bpa.setFeecode(Integer.valueOf(map.get("feecode").toString()));
		}
		return DBI.processAppPayment(USERCODE, bpa, response);
	}
	
	@Override
	public boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		return DBI.processBPApplication(data, response);
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
		return DBI.saveBPASiteInspection(bpa, USERCODE, fromprocesscode, response);
	}

}
