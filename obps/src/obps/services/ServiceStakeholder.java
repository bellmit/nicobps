package obps.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import obps.daos.DaoPaymentInterface;
import obps.util.application.BatchUpdateModel;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Service
public class ServiceStakeholder implements ServiceStakeholderInterface {
	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	private DaoPaymentInterface daoPaymentInterface;

	@Override
	public List<Map<String, Object>> listLicensees() {
		String sql = "SELECT l.*,lt.*,d.*,s.statename,p.processcode,pf.flowname as nextprocessname,app.applicationcode,off.officecode,off.officename1,"
				+ "u.mobileno,u.username as email,"
				+ "(SELECT json_agg(enclosures)from(select e.enclosurecode,e.enclosurename from nicobps.licenseesenclosures le ,masters.enclosures e where e.enclosurecode=le.enclosurecode and le.usercode=l.usercode)as enclosures)as enclosures"
				+ " FROM nicobps.licensees l "
				+ "INNER JOIN masters.licenseetypes lt on lt.licenseetypecode=l.licenseetypecode "
				+ "INNER JOIN masters.districts d on d.districtcode=l.predistrictcode "
				+ "INNER JOIN nicobps.applications app on app.usercode=l.usercode "
				+ "INNER JOIN nicobps.applicationflowremarks afr on  "
				+ "		afr.afrcode=(select max(afrcode) from nicobps.applicationflowremarks where applicationcode=app.applicationcode::text) "
				+ "INNER JOIN masters.processflow pf on afr.toprocesscode=pf.fromprocesscode and pf.processflowstatus='N' and pf.modulecode=1 "
				+ "INNER JOIN masters.processes p on p.processcode=pf.fromprocesscode and p.modulecode=pf.modulecode "
				+ "INNER JOIN nicobps.userlogins u on l.usercode=u.usercode "
				+ "INNER JOIN masters.offices off on off.officecode=app.officecode "
				+ "INNER JOIN masters.states s on s.statecode=d.statecode ORDER BY l.entrydate DESC ";
		List<Map<String, Object>> list = SUI.listGeneric(sql);
		for (Map<String, Object> m : list) {
			m.put("transactions", daoPaymentInterface.getTransaction(m.get("applicationcode").toString()));
		}
		return list;
	}

	@Override
	public byte[] getEnclosure(Integer usercode, Integer enclosurecode) {
		String sql = "Select enclosureimage from nicobps.licenseesenclosures "
				+ "WHERE usercode=? AND enclosurecode=? ";
		return SUI.getBytes(sql, new Object[] { usercode, enclosurecode });
	}

	@Override
	public Map<String, Object> getFeeMaster(Integer officecode, Integer usercode, Integer feetypecode) {
		String sql = "SELECT f.feecode,f.feeamount FROM masters.feemaster f Inner JOIN nicobps.licensees u on u.licenseetypecode=f.licenseetypecode WHERE officecode=? and usercode=? and feetypecode=?";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { officecode, usercode, feetypecode });
		if (list.isEmpty()) {
			return new HashMap<String, Object>();
		} else {
			return list.get(0);
		}
	}

	@Override
	public boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode, String remarks) {
		if (SUI.updateApplicationflowremarks(applicationcode, 1,
				Integer.valueOf(SUI.getNextProcessflow(1, applicationcode).get("toprocesscode").toString()), usercode,
				null, remarks)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode,
			Integer nextprocessode, String remarks) {

		List<BatchUpdateModel> dmlList = new LinkedList<BatchUpdateModel>();

		Integer afrcode = SUI.getMax("nicobps", "applicationflowremarks", "afrcode") + 1;
		String sql = "select toprocesscode from nicobps.applicationflowremarks where afrcode=(select max(afrcode) from nicobps.applicationflowremarks where applicationcode=? )";
		Integer fromprocesscode = (Integer) (SUI.listGeneric(sql, new Object[] { applicationcode })).get(0)
				.get("toprocesscode");
		sql = "INSERT INTO nicobps.applicationflowremarks(afrcode,applicationcode,modulecode,fromprocesscode,toprocesscode,fromusercode,tousercode,remarks) "
				+ "VALUES (?,?,?,?,?,?,?,?) ";
		Object[] values2 = { afrcode, applicationcode, 1, fromprocesscode, nextprocessode, usercode, null, remarks };

		dmlList.add(new BatchUpdateModel(sql, values2));

		List<Map<String, Object>> list = SUI.getAllNextProcessflows(1, nextprocessode);
		if (list.get(0).get("fromprocesscode").equals(list.get(0).get("toprocesscode"))) {
			sql = "INSERT INTO nicobps.licenseeofficesvalidities(applicationcode, usercode, officecode, validfrom, validto, extendedto, extendedby) VALUES (?, ?, ?, ?, ?, ?, ?) ";
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.YEAR, 1);
			c.set(Calendar.MONTH, 3);
			c.set(Calendar.DAY_OF_MONTH, 31);
			for (Map<String, Object> i : SUI.listRegisteringOffices(officecode)) {
				dmlList.add(new BatchUpdateModel(sql, new Object[] { applicationcode, usercode, i.get("officecode"),
						new Date(), c.getTime(), c.getTime(), 0 }));
			}
			
			sql = "SELECT count(*) FROM nicobps.userpages where usercode=? ";
			
			if (SUI.getCount(sql, new Object[] { usercode }) <= 2) {
				sql = "INSERT INTO nicobps.userpages(userpagecode,usercode,urlcode) VALUES (?,?,?) ";
				for (Integer urlcode : new Integer[] { 11, 12, 13, 17, 18, 21, 26, 38 }) {
					dmlList.add(
							new BatchUpdateModel(sql, new Object[] { usercode + "U" + urlcode, usercode, urlcode }));
				}
			}
		}
		return SUI.update(dmlList);
	}

	@Override
	public String ulbRegistration(Integer officecode, Integer usercode) {
		String sql = "select count(*)::int from  nicobps.licenseeofficesvalidities " + "where officecode IN ( "
				+ "	select officecode  " + "	from masters.offices  "
				+ "	where isregisteringoffice='N' and registeringofficecode=? " + ") " + "and usercode=? "
				+ "and validfrom < CURRENT_TIMESTAMP " + "and validto > CURRENT_TIMESTAMP ";
		List<Map<String, Object>> count = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!count.isEmpty()) {
			if ((Integer) count.get(0).get("count") > 0)
				return "ALREADY_REPORTED";
		}
		sql = "INSERT INTO nicobps.applications(applicationslno, applicationcode, officecode, modulecode, usercode, servicetypecode) "
				+ "    VALUES (?, ?, ?, ?, ?, ?)";
		Integer servicetypecode = 1;
		CommonMap application = SUI.generateApplicationcode(officecode, 1, usercode, servicetypecode);
		String applicationcode = application.getKey();
		Integer applicationslno = application.getValue3();
		if (SUI.update("nicobps.applications", sql,
				new Object[] { applicationslno, applicationcode, officecode, 1, usercode, servicetypecode })) {
			SUI.updateApplicationflowremarks(applicationcode, 1, 2, 3, usercode, null, "Payment Initiated");
			return applicationcode;
		} else {
			return "false";
		}
	}

	

	@Override
	public boolean extendValidity(Short officecode, Integer usercode, String extendedto, Integer extendedby) {
		if (SUI.updateextendValidity(officecode, usercode, extendedto, extendedby)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> getValidity(Integer usercode) {
		String sql = "SELECT l.applicantsname,l.usercode,to_char(li.validfrom, 'DD-MM-YYYY') as validfrom,coalesce(to_char(li.extendedto, 'DD-MM-YYYY'), to_char(li.validto, 'DD-MM-YYYY')) as validto, li.officecode,o.officename1,to_char(li.entrydate, 'DD-MM-YYYY') as entrydate\n"
				+ "				FROM nicobps.licensees l \n"
				+ "				INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode \n"
				+ "				INNER JOIN masters.offices o on o.officecode=li.officecode\n"
				+ "				where li.usercode=?  ORDER BY o.officename1 DESC  ";
		return SUI.listGeneric(sql, new Object[] { usercode });
	}
}
