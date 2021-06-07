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
import obps.models.BpaSiteInspection;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Service("BPAService")
public class ServiceBPA implements ServiceBPAInterface {
	private static final Logger LOG = Logger.getLogger(ServiceBPA.class.toGenericString());
	private static final Integer BPAMODULECODE = 2;
	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	private DaoBPAInterface DBI;
	
	Integer getApplicationOfficecode(String applicationcode) {
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
		String sql = "SELECT EDCR.usercode, EDCR.officecode, EDCR.edcrnumber, EDCR.planinfoobject, EDCR.status, TO_CHAR(EDCR.entrydate, 'DD/MM/YYYY') edcrdate,     "
				+ "      APP.applicationcode, APP.officecode, APP.modulecode, APP.usercode, APP.applicationslno, APP.servicetypecode, TO_CHAR(APP.entrydate, 'DD/MM/YYYY') appdate ,  "
				+ "      AF.toprocesscode, PR.processname, PU.pageurl  " 
				+ "FROM nicobps.edcrscrutiny EDCR     "
				+ "LEFT JOIN nicobps.bpaapplications BPA ON BPA.edcrnumber = EDCR.edcrnumber     "
				+ "LEFT JOIN nicobps.applications APP ON APP.applicationcode = BPA.applicationcode   "
				+ "LEFT JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (  "
				+ "	SELECT applicationcode, MAX(entrydate)  " + "	FROM nicobps.applicationflowremarks  "
				+ "	WHERE applicationcode = APP.applicationcode  AND modulecode = APP.modulecode   " 
				+ "	GROUP BY applicationcode)  "
				+ "LEFT JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode   "
				+ "LEFT JOIN masters.processflow PF ON PF.toprocesscode = PR.processcode  AND PF.modulecode = PR.modulecode AND PF.processflowstatus = 'N'  "
				+ "LEFT JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode  "
				+ "WHERE EDCR.status='Accepted' AND EDCR.usercode = ?";
		return SUI.listGeneric(sql, new Object[] { USERCODE });
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
	public Map<String, Object> getApplicationFee(Integer USERCODE, String applicationcode,
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
	public boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) {
		Map<String, Object> map = getApplicationFee(USERCODE, bpa.getApplicationcode(), bpa.getFeetypecode());
		if(map != null) {
			bpa.setTotalamount(Double.valueOf(map.get("feeamount").toString()));
			bpa.setFeecode(Integer.valueOf(map.get("feecode").toString()));
		}
		return DBI.processAppPayment(USERCODE, bpa, response);
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
