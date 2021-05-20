package obps.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
		String sql = "SELECT l.*,lt.*,d.*,p.processcode,pf.flowname as nextprocessname FROM nicobps.licensees l "
				+ "INNER JOIN masters.licenseetypes lt on lt.licenseetypecode=l.licenseetypecode "
				+ "INNER JOIN masters.districts d on d.districtcode=l.predistrictcode "
				+ "INNER JOIN nicobps.applicationflowremarks afr on  "
				+ "		afr.afrcode=(select max(afrcode) from nicobps.applicationflowremarks where appreferencecode=l.usercode::text) "
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
	public boolean updateStakeholder(Integer usercode, Integer nextprocessode, String remarks) {
		return SUI.updateApplicationflowremarks(usercode.toString(), 1, nextprocessode, usercode, null, remarks);
	}

	@Override
	public boolean processPayment(Integer usercode, Integer nextprocessode) {
		String sql = "INSERT INTO nicobps.transactions(transactioncode, usercode, feecode, amount, paymentstatus, sentparameters, "
				+ "						responseparameters, bankcode, responsetext1, responsetext2, responsetext3,entrydate) "
				+ "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE);";
		Integer maxtransactioncode = SUI.getMax("nicobps", "transactions", "transactioncode");
		Integer feecode = 4, amount = 0;
		String payStatus = "S", sentparameters = "", responseparameters = "", bankcode = "", responsetext1 = "",
				responsetext2 = "", responsetext3 = "";
		if (SUI.update("nicobps.transactions", sql,
				new Object[] { maxtransactioncode + 1, usercode, feecode, amount, payStatus, sentparameters,
						responseparameters, bankcode, responsetext1, responsetext2, responsetext3 })) {
			return SUI.updateApplicationflowremarks(usercode.toString(), 1, nextprocessode, usercode, null,
					"Payment complete");
		}
		return false;
	}

}
