/*@author Decent Khongstia*/
package obps.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import obps.models.AppQuestionnaire;
import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaApproval;
import obps.models.BpaOwnerDetail;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.BPAConstants;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Repository("BPADAO")
@Transactional
public class DaoBPA implements DaoBPAInterface {
	private static final Logger LOG = Logger.getLogger(DaoBPA.class.toGenericString());

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean approveBPApplication(BpaApproval bpa, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String applicationcode = bpa.getProcessflow().getApplicationcode();
			List<Object[]> params = new ArrayList<>();
			List<Object[]> params2 = new ArrayList<>();
			List<CommonMap> conditions = bpa.getConditions();
			Object[] param;

			String sql1 = "INSERT INTO nicobps.bpaenclosures(   "
					+ "            appenclosurecode, applicationcode, enclosurecode, enclosureimage,    "
					+ "            entrydate)   " + "VALUES (   "
					+ "	(SELECT COALESCE(MAX(appenclosurecode), 0)+1 FROM nicobps.bpaenclosures),    "
					+ "	?, ?, ?, now()   " + ")   ";

			bpa.getDocuments().forEach(document -> {
				params.add(new Object[] { applicationcode, document.getCode(), document.getFileImage() });
			});
			status = jdbcTemplate.batchUpdate(sql1, params).length == params.size();
			if (!status)
				throw new Exception("Failed to update approval details");
			String sql = "INSERT INTO nicobps.bpaconditions( "
					+ "            bpaenclosurecode, applicationcode, conditiondescription, "
					+ "            entrydate) " + "    VALUES ("
					+ "				(SELECT COALESCE(MAX(bpaenclosurecode), 0)+1 FROM nicobps.bpaconditions), "
					+ "				?, ?, now()" + "		) ";
			conditions.forEach(c -> {
				params2.add(new Object[] { applicationcode, c.getValue() });
			});
			status = jdbcTemplate.batchUpdate(sql, params2).length == params2.size();
			if (!status)
				throw new Exception("Failed to insert application conditions");

			sql = "INSERT INTO nicobps.bpaapproveapplications("
					+ "    applicationcode, permitnumber, remarks, usercode, entrydate)" + "VALUES (?, ?, ?, ?, now())";
			String permitno = generateBuildingPermitNumber(bpa.getProcessflow().getApplicationcode());
			status = jdbcTemplate.update(sql, new Object[] { applicationcode, permitno,
					bpa.getProcessflow().getRemarks(), bpa.getProcessflow().getFromusercode() }) > 0;
			if (!status)
				throw new Exception("Failed to update application status");

			status = commonProcessingFunction(applicationcode, bpa.getProcessflow().getFromusercode(), null, null,
					bpa.getProcessflow().getRemarks(), bpa.getProcessflow().getTousercode(), response);
			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application processed successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to approves building permit application.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "";
			List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAConstants.MODULE_CODE,
					bpa.getApplicationcode());
			Map<String, Object> map = (list != null && !list.isEmpty()) ? list.get(0) : new HashMap<>();
			Integer toprocesscode = null;

			if (map.containsKey("toprocesscode"))
				toprocesscode = Integer.valueOf(map.get("toprocesscode").toString());

			String redirecturl = "paymentconfirmation.htm?modulecode=" + BPAConstants.MODULE_CODE + "&applicationcode="
					+ bpa.getApplicationcode() + "&usercode=" + USERCODE + "&feecode=" + bpa.getFeecode()
					+ "&feeamount=" + bpa.getTotalamount() + "&toprocesscode=" + toprocesscode;
			map.clear();
			map.put("key", redirecturl);
			map.put("value", "");
			map.put("value1", bpa.getApplicationcode());
			response.put("nextProcess", map);
			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
			status = true;
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process building permit application - fee payment.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "INSERT INTO nicobps.bpaenclosures(   "
					+ "            appenclosurecode, applicationcode, enclosurecode, enclosureimage,    "
					+ "            entrydate)   " + "VALUES (   "
					+ "	(SELECT COALESCE(MAX(appenclosurecode), 0)+1 FROM nicobps.bpaenclosures),    "
					+ "	?, ?, ?, now()   " + ")   ";

			if (data.getEnclosures() != null && !data.getEnclosures().isEmpty()) {
				List<Object[]> params = new ArrayList<>();
				data.getEnclosures().forEach(report -> {
					params.add(new Object[] { data.getApplicationcode(), report.getCode(), report.getFileImage() });
				});
				status = jdbcTemplate.batchUpdate(sql, params).length == params.size();
				if (!status)
					throw new Exception("Failed to update bpa enclosures details");
			}

			status = commonProcessingFunction(data.getApplicationcode(), data.getFromusercode(), null, null,
					data.getRemarks(), data.getTousercode(), response);
			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application processed successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process application.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	public boolean rejectBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "INSERT INTO nicobps.bparejectapplications(  "
					+ "    slno, applicationcode, remarks, usercode, entrusted, entrydate)  "
					+ "VALUES ((SELECT COALESCE(MAX(slno), 0)+1 FROM nicobps.bparejectapplications), ?, ?, ?, 'FOR NOW - NA', now())";
			status = jdbcTemplate.update(sql,
					new Object[] { data.getApplicationcode(), data.getRemarks(), data.getFromusercode() }) > 0;

			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application rejected successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process building permit application - app fee payment.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean returnFromCitizenBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		boolean status = false;
		try {
			Integer fromprocesscode = null;
			Integer toprocesscode = null;
			Integer tousercode = null;

			String sql = "SELECT AF.toprocesscode AS fromprocesscode, AF.fromprocesscode AS toprocesscode,   "
					+ "       AF.fromusercode AS tousercode, AF.remarks  " + "FROM nicobps.applicationflowremarks AF  "
					+ "INNER JOIN (  "
					+ "		SELECT applicationcode, MAX(afrcode) afrcode FROM nicobps.applicationflowremarks  "
					+ "		GROUP BY applicationcode  "
					+ ")iAF ON  (iAF.applicationcode, iAF.afrcode) = (AF.applicationcode, AF.afrcode)  "
					+ "WHERE AF.applicationcode = ?";
			Map<String, Object> map = SUI.listGeneric(sql, new Object[] { data.getApplicationcode() }).get(0);
			if (map != null && !map.entrySet().isEmpty()) {
				fromprocesscode = (Integer) map.get("fromprocesscode");
				toprocesscode = (Integer) map.get("toprocesscode");
				tousercode = (Integer) map.get("tousercode");
				status = commonProcessingFunction(data.getApplicationcode(), data.getFromusercode(), fromprocesscode,
						toprocesscode, data.getRemarks(), tousercode, response);

				if (!status)
					throw new Exception("Failed to update application flow");
			}
			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application send successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process building permit application - return from citizen.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response) {
		boolean status = false;
		try {
			Object[] param;
			String sql = "SELECT officecode FROM masters.officelocations WHERE locationcode = ?";
			Integer officecode = jdbcTemplate.queryForObject(sql, Integer.class,
					new Object[] { bpa.getOfficelocationcode() });

			CommonMap map = SUI.generateApplicationcode(officecode, BPAConstants.MODULE_CODE, USERCODE, 1);
			bpa.setApplicationcode(map.getKey());

			sql = "INSERT INTO nicobps.applications(  " + "    applicationcode, officecode, modulecode, usercode,   "
					+ "    applicationslno, servicetypecode, entrydate)  " + "VALUES (  " + "	?, ?, ?, ?,   "
					+ "	(SELECT COALESCE(MAX(applicationslno), 0)+1 FROM nicobps.applications), null, now()  "
					+ "    )  ";

			param = new Object[] { bpa.getApplicationcode(), officecode, BPAConstants.MODULE_CODE, USERCODE };
			status = jdbcTemplate.update(sql, param) > 0;
			if (!status)
				throw new Exception("Error: Failed to insert into applications");

			sql = "INSERT INTO nicobps.bpaapplications(    "
					+ "    applicationcode, edcrnumber, ownershiptypecode, ownershipsubtype,     "
					+ "    plotaddressline1, plotaddressline2, plotvillagetown, plotpincode,     "
					+ "    plotgiscoordinates, officelocationcode, landregistrationdetails,     "
					+ "    landregistrationno, plotidentifier1, plotidentifier2, plotidentifier3,     "
					+ "    holdingno, additionalinfo, entrydate)    " + "VALUES (?, ?, ?, ?,     "
					+ "    ?, ?, ?, ?,     " + "    ?, ?, ?,     " + "    ?, ?, ?, ?,     "
					+ "    ?, ?::json, now())    " + "";

			param = new Object[] { bpa.getApplicationcode(), bpa.getEdcrnumber(), bpa.getOwnershiptypecode(),
					bpa.getOwnershipsubtype(), bpa.getPlotaddressline1(), bpa.getPlotaddressline2(),
					bpa.getPlotvillagetown(), bpa.getPlotpincode(), bpa.getPlotgiscoordinates(),
					bpa.getOfficelocationcode(), bpa.getLandregistrationdetails(), bpa.getLandregistrationno(),
					bpa.getPlotidentifier1(), bpa.getPlotidentifier2(), bpa.getPlotidentifier3(), bpa.getHoldingno(),
					bpa.getAdditionalinfo() };

			status = jdbcTemplate.update(sql, param) > 0;
			if (!status)
				throw new Exception("Error: Failed to insert into bpaapplications");

			sql = "INSERT INTO nicobps.bpaownerdetails(   "
					+ "            ownerdetailcode, applicationcode, salutationcode,    "
					+ "            ownername, relationshiptypecode, relationname,    "
					+ "            mobileno, emailid, preaddressline1, "
					+ "			   preaddressline2, pretownvillage, predistrictcode, "
					+ "			   prepincode, peraddressline1, peraddressline2,   "
					+ "            pertownvillage, perdistrictcode, perpincode, "
					+ "			   additionalinfo, entrydate)   " + " VALUES (	"
					+ "		(SELECT COALESCE(MAX(ownerdetailcode), 0) +1 FROM nicobps.bpaownerdetails),  ?, ?,    "
					+ "		?, ?, ?,    " + "		?, ?, ?,    " + "		?, ?, ?,    " + "		?, ?, ?,    "
					+ "		?, ?, ?,    " + "     ?::json, now()    " + "	)";
			List<Object[]> params = new ArrayList<>();
			if (bpa.getOwnerdetails() != null && !bpa.getOwnerdetails().isEmpty()) {
				for (BpaOwnerDetail od : bpa.getOwnerdetails()) {
					param = new Object[] { bpa.getApplicationcode(), od.getSalutationcode(), od.getOwnername(),
							od.getRelationshiptypecode(), od.getRelationname(), od.getMobileno(), od.getEmailid(),
							od.getPreaddressline1(), od.getPreaddressline2(), od.getPretownvillage(),
							od.getPredistrictcode(), od.getPrepincode(), od.getPeraddressline1(),
							od.getPeraddressline2(), od.getPertownvillage(), od.getPerdistrictcode(),
							od.getPerpincode(), od.getAdditionalinfo() };
					params.add(param);
				}

				status = jdbcTemplate.batchUpdate(sql, params).length == params.size();
			}
			;
			if (!status)
				throw new Exception("Error: Failed to insert to OwnerDetails");

			status = SUI.updateApplicationflowremarks(bpa.getApplicationcode(), BPAConstants.MODULE_CODE,
					BPAConstants.FIRST_PROCESS_CODE, BPAConstants.NEXT_PROCESS_CODE, USERCODE, null,
					"Apply for Building Permit");
			if (!status)
				throw new Exception("Error: Failed to update application flow");

			try {
				sql = "SELECT pageurl as key, processname as value      " + "FROM masters.processflow PF        "
						+ "INNER JOIN masters.processes PR ON PR.processcode = PF.toprocesscode AND PR.modulecode = PF.modulecode   "
						+ "INNER JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
						+ "INNER JOIN nicobps.userpages UP ON UP.urlcode = PU.urlcode       "
						+ "WHERE UP.usercode = ? AND PF.modulecode = ? AND PF.fromprocesscode = ?   "
						+ "AND PF.processflowstatus = 'N'";
				List<CommonMap> list = SUI.listCommonMap(sql,
						new Object[] { USERCODE, BPAConstants.MODULE_CODE, BPAConstants.FIRST_PROCESS_CODE });
				if (list != null && !list.isEmpty()) {
					map = list.get(0);
					map.setValue1(bpa.getApplicationcode());
				}
				response.put("nextProcess", map);
			} catch (Exception e) {
				response.put("nextProcess", "");
			}
			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");

		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to save building permit application.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "";
			status = commonProcessingFunction(bpa.getApplicationcode(), USERCODE, fromprocesscode, null,
					"Building Permit Application Step 2 complete", null, response);
			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to save building permit application - step two.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean saveBPASiteInspection(BpaSiteInspection bpa, Integer USERCODE, Integer fromprocesscode,
			HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "INSERT INTO nicobps.bpaenclosures(   "
					+ "            appenclosurecode, applicationcode, enclosurecode, enclosureimage,    "
					+ "            entrydate)   " + "VALUES (   "
					+ "	(SELECT COALESCE(MAX(appenclosurecode), 0)+1 FROM nicobps.bpaenclosures),    "
					+ "	?, ?, ?, now()   " + ")   ";

			List<Object[]> params = new ArrayList<>();
			bpa.getReports().forEach(report -> {
				params.add(new Object[] { bpa.getApplicationcode(), report.getCode(), report.getFileImage() });
			});
			status = jdbcTemplate.batchUpdate(sql, params).length == params.size();
			if (!status)
				throw new Exception("Failed to update siteinspection details");

			if (bpa.getQuestionnaires() != null && !bpa.getQuestionnaires().isEmpty()) {
				List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
				Map<String, Object> tmap = new HashMap<String, Object>();
				tlist = SUI.getCurrentProcessStatus(BPAConstants.MODULE_CODE, bpa.getApplicationcode());

				params.clear();
				if (tlist != null && !tlist.isEmpty()) {
					tmap = tlist.get(0);
					Integer processcode = (Integer) tmap.get("fromprocesscode");
					status = saveApplicationQuestionnaires(BPAConstants.MODULE_CODE, bpa.getApplicationcode(),
							processcode, bpa.getQuestionnaires(), response);
					if (!status)
						throw new Exception("Failed to update siteinspection questionnaires");
				}
			}

			status = commonProcessingFunction(bpa.getApplicationcode(), USERCODE, fromprocesscode, null,
					bpa.getRemarks(), bpa.getTousercode(), response);
			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to save building permit application - site inspection reports.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Override
	public boolean sendToCitizenBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		boolean status = false;
		try {
			/*
			 * String sql = ""; status = jdbcTemplate.update(sql, new Object[] {
			 * data.getApplicationcode(), data.getRemarks(), data.getFromusercode() }) > 0;
			 * 
			 * if (!status) throw new Exception("Failed to update application flow");
			 */
			Integer fromprocesscode = null;
			Integer toprocesscode = null;

			Map<String, Object> map = getPrevProcess(data.getApplicationcode());
			fromprocesscode = (Integer) map.get("fromprocesscode");
			toprocesscode = (Integer) map.get("toprocesscode");

			status = commonProcessingFunction(data.getApplicationcode(), data.getFromusercode(), fromprocesscode,
					toprocesscode, data.getRemarks(), data.getTousercode(), response);
			if (!status)
				throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application send to citizen successfully.");
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process building permit application - send to citizen.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	boolean commonProcessingFunction(String applicationcode, Integer USERCODE, Integer fromprocesscode,
			Integer toprocesscode, String remarks, Integer tousercode, HashMap<String, Object> response) {
		boolean status = false;
		try {
			List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
			Map<String, Object> tmap = new HashMap<String, Object>();
			/* Integer toprocesscode = 0; */

			if (fromprocesscode == null && toprocesscode == null) {
				tlist = SUI.getCurrentProcessStatus(BPAConstants.MODULE_CODE, applicationcode);
				if (tlist != null && !tlist.isEmpty()) {
					tmap = tlist.get(0);
					fromprocesscode = (Integer) tmap.get("fromprocesscode");
					toprocesscode = (Integer) tmap.get("toprocesscode");
				}
			}

			status = SUI.updateApplicationflowremarks(applicationcode, BPAConstants.MODULE_CODE, fromprocesscode,
					toprocesscode, USERCODE, tousercode, remarks);
			if (!status)
				throw new Exception("Error: Failed to update applicationflow");
			LOG.info("tousercode: " + tousercode);
			if (tousercode == null || tousercode.compareTo(0) < 0 || tousercode == USERCODE) {
				setNextProcessingUrl(applicationcode, USERCODE, fromprocesscode, "N", response);
			} else {
				response.put("nextProcess", new CommonMap());
			}

		} catch (Exception e) {
			status = false;
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	Map<String, Object> getPrevProcess(String applicationcode) {
		try {
			String sql = "SELECT PF.fromprocesscode, PF.toprocesscode FROM nicobps.applications APP    "
					+ "INNER JOIN nicobps.applicationflowremarks AF ON AF.afrcode = (SELECT MAX(afrcode) FROM nicobps.applicationflowremarks WHERE applicationcode = APP.applicationcode)   "
					+ "AND AF.applicationcode = APP.applicationcode   "
					+ "INNER JOIN masters.processflow PF ON PF.fromprocesscode = AF.toprocesscode AND PF.officecode = APP.officecode AND PF.modulecode = AF.modulecode   "
					+ "WHERE PF.processflowstatus = 'P' AND APP.applicationcode = ?";
			return SUI.listGeneric(sql, new Object[] { applicationcode }).get(0);
		} catch (Exception e) {
			return null;
		}
	}

	String generateBuildingPermitNumber(String applicationcode) {
		String permitno = "";
		permitno = "BPA" + applicationcode;
		return permitno;
	}

	void setNextProcessingUrl(String applicationcode, Integer usercode, Integer fromprocesscode, String processFlowFlag,
			HashMap<String, Object> response) {
		CommonMap nextProcess = new CommonMap();
		String sql = "SELECT pageurl as key, processname as value      " + "FROM masters.processflow PF        "
				+ "INNER JOIN masters.processes PR ON PR.processcode = PF.toprocesscode AND PR.modulecode = PF.modulecode   "
				+ "INNER JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
				+ "INNER JOIN nicobps.userpages UP ON UP.urlcode = PU.urlcode       "
				+ "WHERE UP.usercode = ? AND PF.modulecode = ? AND PF.fromprocesscode = ?   "
				+ "AND PF.processflowstatus = ?";
		List<CommonMap> results = SUI.listCommonMap(sql,
				new Object[] { usercode, BPAConstants.MODULE_CODE, fromprocesscode, processFlowFlag });
		if (results != null && !results.isEmpty()) {
			nextProcess = results.get(0);
			nextProcess.setValue1(applicationcode);
		}
		response.put("nextProcess", nextProcess);
	}

	Boolean saveApplicationQuestionnaires(Integer modulecode, String applicationcode, Integer processcode,
			List<AppQuestionnaire> questionnaires, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "INSERT INTO nicobps.applicationsquestionaires(  "
					+ "    aqcode, applicationcode, modulecode, processcode, "
					+ "    questioncode, response, remarks, entrydate)  " + "VALUES ("
					+ "        (SELECT COALESCE(MAX(aqcode), 0)+1 FROM nicobps.applicationsquestionaires), "
					+ "			?, ?, ?, ?,   " + "    		?, ?, now())  ";

			List<Object[]> params = new ArrayList<>();
			questionnaires.forEach(q -> {
				params.add(new Object[] { applicationcode, modulecode, processcode, q.getQuestioncode(),
						q.getResponse(), q.getRemarks() });
			});
			if (params != null && !params.isEmpty()) {
				status = jdbcTemplate.batchUpdate(sql, params).length == params.size();
				if (!status)
					throw new Exception("Failed to update siteinspection questionnaires");
			}
		} catch (Exception e) {
			status = false;
			e.printStackTrace();
		}
		return status;
	}
}
