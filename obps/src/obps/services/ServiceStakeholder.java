package obps.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Map<String, Object>> listLicensees(Integer usercode, Integer officecode) {
		String sql = "SELECT l.*,lt.*,d.*,s.statename,p.processcode,pf.flowname as nextprocessname,app.applicationcode,app.entrydate as applicationdate,off.officecode,off.officename1,"
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
				+ "INNER JOIN masters.states s on s.statecode=d.statecode "
				+ "WHERE case when ?=1 then 1=1 else off.officecode=? end " + "ORDER BY l.entrydate DESC ";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { usercode, usercode });
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

			dmlList.add(new BatchUpdateModel(sql,
					new Object[] { applicationcode, usercode, officecode, new Date(), c.getTime(), null, null }));

//			for (Map<String, Object> i : SUI.listRegisteringOffices(officecode)) {
//				dmlList.add(new BatchUpdateModel(sql, new Object[] { applicationcode, usercode, i.get("officecode"),
//						new Date(), c.getTime(), null, null }));
//			}

			String sqlCount = "SELECT count(*) FROM nicobps.userpages where usercode=? and urlcode=? ";
			Integer count = null;
			sql = "INSERT INTO nicobps.userpages(userpagecode,usercode,urlcode) VALUES (?,?,?) ";
			for (Integer urlcode : new Integer[] { 11, 12, 13, 17, 18, 21, 26, 38 }) {
				count = Integer.valueOf(
						SUI.listGeneric(sqlCount, new Object[] { usercode, urlcode }).get(0).get("count").toString());
				if (count == null || count == 0) {
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
	public Map<String, Object> getLicenceeValidity(Integer usercode, Integer officecode) {
		Map<String, Object> response = new HashMap<String, Object>();

		// -------------------------
		// -------Valid
		// -------------------------
		
		
		
		String sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE, "
				+ "(O.OFFICENAME1::TEXT || CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2 ELSE ''::CHARACTER VARYING END::TEXT) || "
				+ "CASE WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3 ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE, "
				+ "validto,extendedto,CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END AS VALIDITY, "
				+ "CASE WHEN EXTENDEDTO IS NULL THEN 'VALID UPTO : ' || TO_CHAR(VALIDTO, 'dd-mm-yyyy') ELSE 'VALID UPTO : ' || TO_CHAR(EXTENDEDTO, 'dd-mm-yyyy') END AS STATUS, "
				+ "CASE WHEN VALIDTO > CURRENT_DATE THEN 1 ELSE 2 END AS STATUSCODE "
				+ "FROM nicobps.USERLOGINS U, MASTERS.OFFICES O, nicobps.LICENSEEOFFICESVALIDITIES V, nicobps.APPLICATIONS A "
				+ "WHERE 1 = 1 AND U.USERCODE = V.USERCODE AND O.OFFICECODE = V.OFFICECODE AND (O.OFFICECODE = A.OFFICECODE AND U.USERCODE = A.USERCODE AND MODULECODE IN (1,3) ) "
				+ "and CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END >= current_date "
				+ "AND A.APPLICATIONCODE = V.APPLICATIONCODE AND O.OFFICECODE = ? AND U.USERCODE = ? "
				+ "ORDER BY CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END DESC LIMIT 1";
		List<Map<String, Object>> list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!list.isEmpty()) {
			System.out.println("1");
			response.put("VALID_LICENCE", "You are already empanelled " + list.get(0).get("office")
					+ " and its valid till " + list.get(0).get("validity"));
			return response;
		}
		
		
		
		
		
		
			
		// -------------------------
		// -------Expired
		// -------------------------
		sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE, "
				+ "(O.OFFICENAME1::TEXT || CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2 ELSE ''::CHARACTER VARYING END::TEXT) || "
				+ "CASE WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3 ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE, "
				+ "validto,extendedto,CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END AS VALIDITY, "
				+ "CASE WHEN EXTENDEDTO IS NULL THEN 'VALID UPTO : ' || TO_CHAR(VALIDTO, 'dd-mm-yyyy') ELSE 'VALID UPTO : ' || TO_CHAR(EXTENDEDTO, 'dd-mm-yyyy') END AS STATUS, "
				+ "CASE WHEN VALIDTO > CURRENT_DATE THEN 1 ELSE 2 END AS STATUSCODE "
				+ "FROM nicobps.USERLOGINS U, MASTERS.OFFICES O, nicobps.LICENSEEOFFICESVALIDITIES V, nicobps.APPLICATIONS A "
				+ "WHERE 1 = 1 AND U.USERCODE = V.USERCODE AND O.OFFICECODE = V.OFFICECODE AND (O.OFFICECODE = A.OFFICECODE AND U.USERCODE = A.USERCODE AND MODULECODE IN (1,3) ) "
				+ "and CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END < current_date "
				+ "AND A.APPLICATIONCODE = V.APPLICATIONCODE AND O.OFFICECODE = ? AND U.USERCODE = ? "
				+ "ORDER BY CASE WHEN EXTENDEDTO IS NULL THEN VALIDTO ELSE EXTENDEDTO END DESC LIMIT 1";
		list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!list.isEmpty()) {
			System.out.println("2");
			response.put("EXPIRED_LICENCE", "Your emnpanelment with " + list.get(0).get("office") + " has expired on "
					+ list.get(0).get("validity") + ".Please click on the button above to renew your empanelment.");
//			return response; // Should it be returned from here?
		}
		
		
		// -------------------------
				// -------Verified and awaiting Payment
				// -------------------------
				sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE,  "
						+ "(O.OFFICENAME1::TEXT || CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2 ELSE ''::CHARACTER VARYING END::TEXT) || "
						+ "         CASE  WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3  ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE "
						+ "             "
						+ "FROM  nicobps.USERLOGINS U INNER JOIN nicobps.APPLICATIONS A ON U.USERCODE = A.USERCODE  "
						+ "INNER JOIN MASTERS.OFFICES O ON O.OFFICECODE = A.OFFICECODE "
						+ "INNER JOIN nicobps.APPLICATIONFLOWREMARKS AFR ON (A.APPLICATIONCODE = AFR.APPLICATIONCODE) "
						+ " LEFT OUTER JOIN nicobps.applicationstransactionmap ATM ON A.APPLICATIONCODE = ATM.APPLICATIONCODE  "
						+ " WHERE 1 = 1  AND TOPROCESSCODE = 5 " + " AND ATM.APPLICATIONCODE IS NULL  " + "AND O.OFFICECODE = ?  "
						+ " AND U.USERCODE  = ? " + " AND A.MODULECODE IN (1, 3) "
						+ " ORDER BY A.MODULECODE, U.USERCODE, AFR.ENTRYDATE DESC LIMIT 1";
				list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
				if (!list.isEmpty()) {
					System.out.println("3");
					
					
					
					response.put("VERIFIED",
							"Your application for emnpanelment with " + list.get(0).get("office")
									+ " office bearing application code " + list.get(0).get("applicationcode")
									+ " has been verified. Please pay the Registration Fee. click on the Home Link > Payment of Registration Fees.");
					
					return response;
				}
		
		
		
		// -------------------------
		// -------Awaiting Verification
		// -------------------------
		sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE,  "
				+ "(O.OFFICENAME1::TEXT || CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2 ELSE ''::CHARACTER VARYING END::TEXT) || "
				+ "         CASE  WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3  ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE "
				+ "             "
				+ "FROM  nicobps.USERLOGINS U INNER JOIN nicobps.APPLICATIONS A ON U.USERCODE = A.USERCODE  "
				+ "INNER JOIN MASTERS.OFFICES O ON O.OFFICECODE = A.OFFICECODE "
				+ "INNER JOIN nicobps.APPLICATIONFLOWREMARKS AFR ON (A.APPLICATIONCODE = AFR.APPLICATIONCODE) "
				+ " LEFT OUTER JOIN nicobps.applicationstransactionmap ATM ON A.APPLICATIONCODE = ATM.APPLICATIONCODE  "
				+ "WHERE 1 = 1  " + "AND ATM.APPLICATIONCODE IS NULL  " + "AND O.OFFICECODE = ?  "
				+ "AND U.USERCODE  = ? " + "AND A.MODULECODE IN (1, 3) "
				+ "ORDER BY A.MODULECODE, U.USERCODE, AFR.ENTRYDATE DESC LIMIT 1";
		
		list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!list.isEmpty()) {
			System.out.println("4");
			response.put("VERIFICATION_PENDING",
					"Your application for emnpanelment with " + list.get(0).get("office")
							+ " office bearing application code " + list.get(0).get("applicationcode")
							+ " is under process and awaiting verification.");
			return response;
		}

		// -------------------------
		// -------Paid and Awaiting Approval
		// -------------------------
		sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE,  "
				+ "	    (O.OFFICENAME1::TEXT ||    CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2  ELSE ''::CHARACTER VARYING END::TEXT) || "
				+ "	    CASE  WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3  ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE "
				+ "              "
				+ "FROM  nicobps.USERLOGINS U INNER JOIN nicobps.APPLICATIONS A ON U.USERCODE = A.USERCODE  "
				+ "INNER JOIN MASTERS.OFFICES O ON O.OFFICECODE = A.OFFICECODE "
				+ "INNER JOIN nicobps.APPLICATIONFLOWREMARKS AFR ON (A.APPLICATIONCODE = AFR.APPLICATIONCODE) "
				+ "INNER JOIN nicobps.applicationstransactionmap ATM ON A.APPLICATIONCODE = ATM.APPLICATIONCODE  "
				+ "INNER JOIN nicobps.TRANSACTIONS T ON T.TRANSACTIONCODE = ATM.TRANSACTIONCODE  "
				+ "LEFT OUTER JOIN nicobps.LICENSEEOFFICESVALIDITIES V ON A.APPLICATIONCODE = V.APPLICATIONCODE  "
				+ "WHERE A.MODULECODE IN (1, 3) " + "AND PAYMENTSTATUS='S' "
				+ "AND O.OFFICECODE = ? AND U.USERCODE  = ? "
				+ "ORDER BY A.MODULECODE, U.USERCODE, AFR.ENTRYDATE DESC LIMIT 1";
		list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!list.isEmpty()) {
			System.out.println("5");
			response.put("AWAITING_APPROVAL",
					"Your application for emnpanelment with " + list.get(0).get("office")
							+ " office bearing application code " + list.get(0).get("applicationcode")
							+ " is under process and awaiting approval.");
			return response;
		}
		// -------------------------
		// -------Payment Cannot be ascertained
		// -------------------------
		sql = "SELECT U.USERCODE, U.USERNAME, A.APPLICATIONCODE,  "
				+ "	    (O.OFFICENAME1::TEXT ||    CASE WHEN O.OFFICENAME2 IS NOT NULL THEN O.OFFICENAME2  ELSE ''::CHARACTER VARYING END::TEXT) || "
				+ "	    CASE  WHEN O.OFFICENAME3 IS NOT NULL THEN O.OFFICENAME3  ELSE ''::CHARACTER VARYING END::TEXT AS OFFICE "
				+ "              "
				+ "FROM  nicobps.USERLOGINS U INNER JOIN nicobps.APPLICATIONS A ON U.USERCODE = A.USERCODE  "
				+ "INNER JOIN MASTERS.OFFICES O ON O.OFFICECODE = A.OFFICECODE "
				+ "INNER JOIN nicobps.APPLICATIONFLOWREMARKS AFR ON (A.APPLICATIONCODE = AFR.APPLICATIONCODE) "
				+ "INNER JOIN nicobps.applicationstransactionmap ATM ON A.APPLICATIONCODE = ATM.APPLICATIONCODE  "
				+ "INNER JOIN nicobps.TRANSACTIONS T ON T.TRANSACTIONCODE = ATM.TRANSACTIONCODE  "
				+ "LEFT OUTER JOIN nicobps.LICENSEEOFFICESVALIDITIES V ON A.APPLICATIONCODE = V.APPLICATIONCODE  "
				+ "WHERE A.MODULECODE IN (1, 3) " + "AND PAYMENTSTATUS='I' "
				+ "AND O.OFFICECODE = ? AND U.USERCODE  = ? "
				+ "ORDER BY A.MODULECODE, U.USERCODE, AFR.ENTRYDATE DESC LIMIT 1";
		list = SUI.listGeneric(sql, new Object[] { officecode, usercode });
		if (!list.isEmpty()) {
			System.out.println("6");
			response.put("PAYMENT_INITIATED", "Your application for emnpanelment with " + list.get(0).get("office")
					+ " office bearing application code " + list.get(0).get("applicationcode")
					+ " is under process. The payment status of the application cannot be ascertained at the moment.");
			return response;
		}
		return response;
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
		String sql = "SELECT l.applicantsname,l.usercode,to_char(li.validfrom, 'DD-MM-YYYY') as validfrom,coalesce(to_char(li.extendedto, 'DD-MM-YYYY'), to_char(li.validto, 'DD-MM-YYYY')) as validto, li.officecode,o.officename1,to_char(li.entrydate, 'DD-MM-YYYY') as entrydate "
				+ "				FROM nicobps.licensees l "
				+ "				INNER JOIN nicobps.licenseeofficesvalidities li on li.usercode=l.usercode "
				+ "				INNER JOIN masters.offices o on o.officecode=li.officecode "
				+ "				where li.usercode=?  ORDER BY o.officename1 DESC  ";
		return SUI.listGeneric(sql, new Object[] { usercode });
	}
}
