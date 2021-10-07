package obps.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.util.application.ServiceUtilInterface;

@Service("ServicePrintPermit")
public class ServicePrintPermit implements ServicePrintPermitInterface {

	@Autowired
	private ServiceUtilInterface SUI;

	@Override
	public List<Map<String, Object>> getPermitList(String applicationcode, String permitnumber, String edcrnumber,
			String ownername, String fromentrydate, String toentrydate, String criteria) {

		String sql = "select ba.applicationcode,ba.edcrnumber,bo.ownername,EDCR.originalfilename, "
		        + " (plotaddressline1 || ' '|| plotaddressline2 || ' '|| plotvillagetown ) as address, "
				+ " AF.fromprocesscode, PRF.processname AS fromprocessname,AF.toprocesscode, PR.processname AS toprocessname,  "
				+ " ba.landregistrationdetails,ba.ownershipsubtype,bap.permitnumber,bap.remarks,"
				+ " to_char(bap.entrydate, 'DD-MM-YYYY') as entrydate" + " from nicobps.bpaapplications ba "
				+ " inner join nicobps.bpaownerdetails bo on ba.applicationcode=bo.applicationcode "
				+ " inner join nicobps.bpaapproveapplications bap on ba.applicationcode=bap.applicationcode  "
				+ " inner join nicobps.edcrscrutiny EDCR on EDCR.edcrnumber= ba.edcrnumber "
				+ " INNER JOIN nicobps.applicationflowremarks AF ON (AF.applicationcode, AF.entrydate) = (    "  
				+ "	SELECT applicationcode, MAX(entrydate)  FROM nicobps.applicationflowremarks   "   
			    + "	WHERE applicationcode = ba.applicationcode "
				+ "	GROUP BY applicationcode)   "
				+ " INNER JOIN masters.processes PRF ON PRF.processcode = AF.fromprocesscode AND PRF.modulecode = AF.modulecode   "
				+ "INNER JOIN masters.processes PR ON PR.processcode = AF.toprocesscode AND PR.modulecode = AF.modulecode "
				+ " where ";

		if (criteria.equals("byappcode")) {
			sql += " upper(ba.applicationcode)=upper('" + applicationcode + "') ;";
		} else if (criteria.equals("byedcrno")) {
			sql += "upper(ba.edcrnumber)=upper('" + edcrnumber + "') ;";
		} else if (criteria.equals("byowner")) {
			sql += " lower(bo.ownername) like lower('%" + ownername + "%') ;";
		} else if (criteria.equals("byentrydate")) {
			sql += "  DATE(bap.entrydate) BETWEEN to_date('" + fromentrydate + "','dd-mm-yyyy') and to_date('"+ toentrydate + "','dd-mm-yyyy') ;";
		} else if (criteria.equals("bypermitno")) {
			sql += " upper(bap.permitnumber)=upper('" + permitnumber + "') ;";
		}

		return SUI.listGeneric(sql);
	}

}