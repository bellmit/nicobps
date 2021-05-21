package obps.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Service
public class ServiceStakeholder implements ServiceStakeholderInterface {
	@Autowired
	private ServiceUtilInterface SUI;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String, Object>> listLicensees() {
		String sql = "SELECT l.*,lt.*,d.*,p.processcode,pf.flowname as nextprocessname,app.applicationcode,app.officecode FROM nicobps.licensees l "
				+ "INNER JOIN masters.licenseetypes lt on lt.licenseetypecode=l.licenseetypecode "
				+ "INNER JOIN masters.districts d on d.districtcode=l.predistrictcode "
				+ "INNER JOIN nicobps.applications app on app.usercode=l.usercode "
				+ "INNER JOIN nicobps.applicationflowremarks afr on  "
				+ "		afr.afrcode=(select max(afrcode) from nicobps.applicationflowremarks where appreferencecode=app.applicationcode::text) "
				+ "INNER JOIN masters.processflow pf on afr.toprocesscode=pf.fromprocesscode and pf.processflowstatus='N' "
				+ "INNER JOIN masters.processes p on p.processcode=pf.fromprocesscode ORDER BY l.entrydate DESC ";
		return SUI.listGeneric(sql);
	}

	@Override
	public byte[] getEnclosure(Integer usercode, Integer enclosurecode) {
		String sql = "Select enclosureimage from nicobps.licenseesenclosures "
				+ "WHERE usercode=? AND enclosurecode=? ";
		return SUI.getBytes(sql, new Object[] { usercode, enclosurecode });
	}

	@Override
	public boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode,
			Integer nextprocessode, String remarks) {
		if (SUI.updateApplicationflowremarks(applicationcode, 1, nextprocessode, usercode, null, remarks)) {
			List<Map<String, Object>> list = SUI.getNextProcessflow(1, nextprocessode);
			if (list.get(0).get("fromprocesscode").equals(list.get(0).get("toprocesscode"))) {
				String sql = "INSERT INTO nicobps.useroffices(usercode, officecode)VALUES (?, ?)";
				for (Map<String, Object> i : SUI.listRegisteringOffices(officecode)) {
					SUI.update("", sql, new Object[] { usercode, i.get("officecode") });
				}
				sql = "INSERT INTO nicobps.licenseeofficesvalidities(applicationcode, usercode, officecode, validfrom, validto) "
						+ "    VALUES (?, ?, ?, ?, ?)";
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				c.add(Calendar.YEAR, 1);
				SUI.update("nicobps.applications", sql,
						new Object[] { applicationcode, usercode, officecode, new Date(), c.getTime() });
			}
			return true;
		}
		return false;
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
	public String ulbRegistration(Integer officecode, Integer usercode) {
		String sql = "INSERT INTO nicobps.applications(applicationslno, applicationcode, officecode, modulecode, usercode, applicationtype) "
				+ "    VALUES (?, ?, ?, ?, ?, ?)";
		Integer applicationslno = SUI.getMax("nicobps", "applications", "applicationslno");
		applicationslno++;
		String applicationcode = String.format("%03d", officecode) + "001" + String.format("%05d", usercode.toString())
				+ String.format("%08d", applicationslno);
		if (SUI.update("nicobps.applications", sql,
				new Object[] { applicationslno, applicationcode, officecode, 1, usercode, "F" })) {
			SUI.updateApplicationflowremarks(applicationcode, 1, 2, 3, usercode, null, "Payment Initiated");
			return applicationcode;
		} else {
			return "false";
		}
	}

	@Override
	public boolean processPayment(Integer usercode, String applicationcode, Integer feecode, Integer fee,
			Integer nextprocessode) {
		String sql = "INSERT INTO nicobps.transactions(transactioncode, usercode, feecode, amount, paymentstatus, sentparameters, "
				+ "						responseparameters, bankcode, responsetext1, responsetext2, responsetext3,entrydate) "
				+ "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE);";
		Integer maxtransactioncode = SUI.getMax("nicobps", "transactions", "transactioncode");
		String payStatus = "S", sentparameters = "", responseparameters = "", bankcode = "", responsetext1 = "",
				responsetext2 = "", responsetext3 = "";
		if (SUI.update("nicobps.transactions", sql,
				new Object[] { maxtransactioncode + 1, usercode, feecode, fee, payStatus, sentparameters,
						responseparameters, bankcode, responsetext1, responsetext2, responsetext3 })) {
			return SUI.updateApplicationflowremarks(applicationcode, 1, nextprocessode, usercode, null,
					"Payment complete");
		}
		return false;
	}

}
