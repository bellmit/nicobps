package obps.reports;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.util.application.ServiceUtilInterface;

@Service
public class ServicePrintPermit implements ServicePrintPermitInterface {

	@Autowired
	private ServiceUtilInterface SUI;

	@Override
	public List<Map<String, Object>> getPermitList(String applicationcode, String edcrnumber, String ownername,
			String fromentrydate, String toentrydate, String criteria) {

		String sql = " select ba.applicationcode,ba.edcrnumber,bo.ownername,to_char(ba.entrydate, 'DD-MM-YYYY') as entrydate,ba.landregistrationdetails,ba.ownershipsubtype from nicobps.bpaapplications ba "
				+ " inner join nicobps.bpaownerdetails bo on ba.applicationcode=bo.applicationcode "
				+ " inner join nicobps.applicationflowremarks af on ba.applicationcode=af.applicationcode " + " where ";

		if (criteria.equals("byappcode")) {
			sql += " upper(ba.applicationcode)=upper('" + applicationcode + "') ;";
		} else if (criteria.equals("byedcrno")) {
			sql += "upper( ba.edcrnumber)=upper('" + edcrnumber + "') ;";
		} else if (criteria.equals("byowner")) {
			sql += " lower(bo.ownername) like lower('%" + ownername + "%') ;";
		} else if (criteria.equals("byentrydate")) {
			sql += " ba.entrydate>=to_date('" + fromentrydate + "','dd-mm-yyyy') and ba.entrydate<=to_date('"
					+ toentrydate + "','dd-mm-yyyy') ;";
		}

		return SUI.listGeneric(sql);
	}

}
