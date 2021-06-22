/*@author Decent Khongstia*/
package obps.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaOwnerDetail;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Repository("BPADAO")
@Transactional
public class DaoBPA implements DaoBPAInterface{
	private static final Logger LOG = Logger.getLogger(DaoBPA.class.toGenericString());
	
	private final Integer BPAMODULECODE = 2;
	private final Integer BPAFIRSTPROCESSCODE = 1;
	private final Integer BPANEXTPROCESSCODE = 2;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceUtilInterface SUI;
	
	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean processBPApplication(BpaProcessFlow data, HashMap<String, Object> response) {
		boolean status = false;
		try {
			status = commonProcessingFunction(data.getApplicationcode(), data.getFromusercode(), null,
					data.getRemarks(), data.getTousercode(), response);
			if(!status) throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application processed successfully.");
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
	public boolean processAppPayment(Integer USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "";
			List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAMODULECODE, bpa.getApplicationcode());
			Map<String, Object> map = list.get(0);
			Integer toprocesscode;
			if (map != null) {
				toprocesscode = Integer.valueOf(map.get("toprocesscode").toString());
				String redirecturl = "paymentconfirmation.htm?modulecode=" + BPAMODULECODE
						+ "&applicationcode=" + bpa.getApplicationcode() + "&usercode=" + USERCODE + "&feecode="
						+ bpa.getFeecode() + "&feeamount=" + bpa.getTotalamount() + "&toprocesscode=" + toprocesscode;
				map.clear();
				map.put("key", redirecturl);
				map.put("value", "");
				map.put("value1", bpa.getApplicationcode());
				response.put("nextProcess", map);
				response.put("code", HttpStatus.CREATED.value());
				response.put("msg", "Success: Application saved successfully.");
				status = true;
			} else {
				response.put("code", HttpStatus.BAD_REQUEST.value());
				response.put("msg", "Error: Failed to get processcode.");
			}
		} catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to process building permit application - app fee payment.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}
	
	/*
	 * @Override
	 * 
	 * @Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	 * Exception.class, readOnly = false) public boolean processAppPayment(Integer
	 * USERCODE, BpaApplicationFee bpa, HashMap<String, Object> response) { boolean
	 * status = false; try { String sql =
	 * "INSERT INTO nicobps.transactions(transactioncode, usercode, feecode, paymentmodecode, amount, paymentstatus, sentparameters, "
	 * +
	 * "						responseparameters, bankcode, responsetext1, responsetext2, responsetext3,entrydate) "
	 * + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE);"; Integer
	 * maxtransactioncode = SUI.getMax("nicobps", "transactions",
	 * "transactioncode"); String payStatus = "S", sentparameters = "",
	 * responseparameters = "", bankcode = "", responsetext1 = "", responsetext2 =
	 * "", responsetext3 = "";
	 * 
	 * List<Map<String, Object>> list = SUI.getCurrentProcessStatus(BPAMODULECODE,
	 * bpa.getApplicationcode()); if(list != null && !list.isEmpty()) { Map<String,
	 * Object> map = list.get(0); Integer fromprocesscode, toprocesscode; if(map !=
	 * null) { LOG.info(map.toString()); fromprocesscode =
	 * Integer.valueOf(map.get("fromprocesscode").toString()); toprocesscode =
	 * Integer.valueOf(map.get("toprocesscode").toString()); status =
	 * SUI.updateApplicationflowremarks(bpa.getApplicationcode(), BPAMODULECODE,
	 * fromprocesscode, toprocesscode, USERCODE, null,
	 * "BPA - Application Fee Payment complete"); if(!status) throw new
	 * Exception("Error: Failed to process bpa application fee payment");
	 * 
	 * status = SUI.update("nicobps.transactions", sql, new Object[] {
	 * maxtransactioncode + 1, USERCODE, bpa.getFeetypecode(), bpa.getPaymentmode(),
	 * bpa.getTotalamount(), payStatus, sentparameters, responseparameters,
	 * bankcode, responsetext1, responsetext2, responsetext3 }); String redirecturl
	 * = "paysuccess.htm?modulecode=1&applicationcode=" + bpa.getApplicationcode() +
	 * "&usercode=" + USERCODE + "&feecode=" + bpa.getFeecode() + "&feeamount=" +
	 * bpa.getTotalamount() + "&toprocesscode=" + toprocesscode; map.clear();
	 * map.put("key", redirecturl); map.put("value", ""); map.put("value1",
	 * bpa.getApplicationcode()); response.put("nextProcess", map);
	 * response.put("code", HttpStatus.CREATED.value()); response.put("msg",
	 * "Success: Application saved successfully."); } }else { response.put("code",
	 * HttpStatus.BAD_REQUEST.value()); response.put("msg",
	 * "Error: Failed to get processcode."); } }catch (Exception e) {
	 * response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
	 * response.put("msg",
	 * "Error: Failed to process building permit application - app fee payment.");
	 * status = false; e.printStackTrace(); LOG.log(Level.SEVERE,
	 * e.getLocalizedMessage()); } return status; }
	 */
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	public boolean saveBPA(BpaApplication bpa, Integer USERCODE, HashMap<String, Object> response) {
		boolean status = false;
		try {
			Object [] param;
			String sql = "SELECT officecode FROM masters.officelocations WHERE locationcode = ?";
			Integer officecode = jdbcTemplate.queryForObject(sql, Integer.class, new Object[] {bpa.getOfficelocationcode()});
			
			CommonMap map = SUI.generateApplicationcode(officecode, BPAMODULECODE, USERCODE, 1);
			bpa.setApplicationcode(map.getKey());
			
			sql = "INSERT INTO nicobps.applications(  " + 
					"    applicationcode, officecode, modulecode, usercode,   " + 
					"    applicationslno, servicetypecode, entrydate)  " + 
					"VALUES (  " + 
					"	?, ?, ?, ?,   " + 
					"	(SELECT COALESCE(MAX(applicationslno), 0)+1 FROM nicobps.applications), null, now()  " + 
					"    )  ";
			
			param = new Object[] {
					bpa.getApplicationcode(), officecode, BPAMODULECODE, USERCODE 
			};
			status = jdbcTemplate.update(sql, param) > 0;
			if(!status) throw new Exception("Error: Failed to insert into applications");
			
			sql = "INSERT INTO nicobps.bpaapplications(   " + 
					"            applicationcode, edcrnumber, ownershiptypecode,    " + 
					"            ownershipsubtype, plotaddressline1, plotaddressline2,    " + 
					"            plotvillagetown, plotpincode,    " + 
					"            plotgiscoordinates, officelocationcode, landregistrationdetails,    " + 
					"            landregistrationno, plotidentifier1, plotidentifier2,    " + 
					"            plotidentifier3, holdingno, entrydate)   " + 
					"VALUES (   " + 
					"	?, ?, ?,    " + 
					"	?, ?, ?,    " + 
					"	?, ?,    	" + 
					"	?, ?, ?,    " + 
					"	?, ?, ?,    " + 
					"	?, ?, now()   " + 
					")";
			param = new Object[] {
					bpa.getApplicationcode(), bpa.getEdcrnumber(), bpa.getOwnershiptypecode(),
					bpa.getOwnershipsubtype(), bpa.getPlotaddressline1(), bpa.getPlotaddressline2(),
					bpa.getPlotvillagetown(), bpa.getPlotpincode(),
					bpa.getPlotgiscoordinates(), bpa.getOfficelocationcode(), bpa.getLandregistrationdetails(),
					bpa.getLandregistrationno(), bpa.getPlotidentifier1(), bpa.getPlotidentifier2(),
					bpa.getPlotidentifier3(), bpa.getHoldingno()
			};
			
			status = jdbcTemplate.update(sql, param) > 0;
			if(!status) throw new Exception("Error: Failed to insert into bpaapplications");
			
			sql = "INSERT INTO nicobps.bpaownerdetails(   "
					+ "            ownerdetailcode, "
					+ "			   applicationcode, salutationcode,    "
					+ "            ownername, relationshiptypecode, relationname,    "
					+ "            mobileno, emailid, address,    " 
					+ "            entrydate)   "
					+ " VALUES (	"
					+ "		(SELECT COALESCE(MAX(ownerdetailcode), 0) +1 FROM nicobps.bpaownerdetails), "
					+ "		?, ?,    " 
					+ "		?, ?, ?,    " 
					+ "		?, ?, ?,    "
					+ "     now()"
					+ "	)";
			List<Object[]> params = new ArrayList<>();
			if(bpa.getOwnerdetails() != null && !bpa.getOwnerdetails().isEmpty()) {
				for(BpaOwnerDetail od: bpa.getOwnerdetails()) {
					param = new Object[] {
							bpa.getApplicationcode(), od.getSalutationcode(),
							od.getOwnername(), od.getRelationshiptypecode(), od.getRelationname(),
							od.getMobileno(), od.getEmailid(), od.getAddress()
					};
					params.add(param);
				}
				
				status = jdbcTemplate.batchUpdate(sql, params).length == params.size();
			};
			if(!status) throw new Exception("Error: Failed to insert to OwnerDetails");
			
			status = SUI.updateApplicationflowremarks(bpa.getApplicationcode(), BPAMODULECODE, BPAFIRSTPROCESSCODE, BPANEXTPROCESSCODE, USERCODE, null, "Apply for Building Permit");
			if(!status) throw new Exception("Error: Failed to update application flow");

			try {
				sql = "SELECT pageurl as key, processname as value      " 
						+ "FROM masters.processflow PF        "
						+ "INNER JOIN masters.processes PR ON PR.processcode = PF.toprocesscode AND PR.modulecode = PF.modulecode   "
						+ "INNER JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
						+ "INNER JOIN nicobps.userpages UP ON UP.urlcode = PU.urlcode       "
						+ "WHERE UP.usercode = ? AND PF.modulecode = ? AND PF.fromprocesscode = ?   " 
						+ "AND PF.processflowstatus = 'N'";
				List<CommonMap> list = SUI.listCommonMap(sql, new Object[] {USERCODE,BPAMODULECODE, BPAFIRSTPROCESSCODE}); 
				if(list != null && !list.isEmpty()) {
					map = list.get(0);
					map.setValue1(bpa.getApplicationcode());
				}
				response.put("nextProcess", map);
			}catch (Exception e) {
				response.put("nextProcess", "");
			}
			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
				
		}catch (Exception e) {
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
	public boolean saveBPAStepTwo(BpaApplication bpa, Integer USERCODE, Integer fromprocesscode, HashMap<String, Object> response) {
		boolean status = false;
		try {
			String sql = "";
			status = commonProcessingFunction(bpa.getApplicationcode(), USERCODE, fromprocesscode, "Building Permit Application Step 2 complete", USERCODE, response);
			if(!status) throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
		}catch (Exception e) {
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
			String sql = "INSERT INTO nicobps.bpasiteinspectiondetails(  "
					+ "    appenclosurecode, applicationcode, enclosurecode, enclosureimage,   " + "    entrydate)  "
					+ "VALUES ((SELECT COALESCE(MAX(appenclosurecode), 0) + 1 FROM nicobps.bpasiteinspectiondetails), ?, null, ?, now())  ";
			
			Object[] param = new Object[] {
					bpa.getApplicationcode(), bpa.getImageFile()
			};
			status = jdbcTemplate.update(sql, param) > 0;
			if(!status) throw new Exception("Failed to update siteinspection details");
			
			status = commonProcessingFunction(bpa.getApplicationcode(), USERCODE, fromprocesscode, bpa.getRemarks(), bpa.getTousercode(), response);
			if(!status) throw new Exception("Failed to update application flow");

			response.put("code", HttpStatus.CREATED.value());
			response.put("msg", "Success: Application saved successfully.");
		}catch (Exception e) {
			response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("msg", "Error: Failed to save building permit application - site inspection.");
			status = false;
			e.printStackTrace();
			LOG.log(Level.SEVERE, e.getLocalizedMessage());
		}
		return status;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
	boolean commonProcessingFunction(String applicationcode, Integer USERCODE, Integer fromprocesscode, String remarks,
			Integer tousercode, HashMap<String, Object> response) {
		boolean status = false;
		try {
			// TODO: handle exception
			
			List<Map<String, Object>> tlist = new ArrayList<Map<String,Object>>();
			Map<String, Object> tmap = new HashMap<String, Object>();
			Integer toprocesscode = 0;
			
			if(fromprocesscode == null) {
				tlist = SUI.getCurrentProcessStatus(BPAMODULECODE, applicationcode);
				if(tlist != null && !tlist.isEmpty()) {
					tmap = tlist.get(0);
					fromprocesscode = (Integer) tmap.get("fromprocesscode");
					toprocesscode = (Integer) tmap.get("toprocesscode");
				}
			}
			
			status = SUI.updateApplicationflowremarks(applicationcode, BPAMODULECODE, fromprocesscode, toprocesscode,
					USERCODE, tousercode, remarks);
			if(!status) throw new Exception("Error: Failed to update applicationflow");
			
			String sql = "";
			CommonMap map = new CommonMap();
			if(tousercode != null && tousercode.compareTo(-1) > 0) {
				try {
					sql = "SELECT pageurl as key, processname as value      " 
							+ "FROM masters.processflow PF        "
							+ "INNER JOIN masters.processes PR ON PR.processcode = PF.toprocesscode AND PR.modulecode = PF.modulecode   "
							+ "INNER JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode       "
							+ "INNER JOIN nicobps.userpages UP ON UP.urlcode = PU.urlcode       "
							+ "WHERE UP.usercode = ? AND PF.modulecode = ? AND PF.fromprocesscode = ?   " 
							+ "AND PF.processflowstatus = 'N'";
					List<CommonMap> list = SUI.listCommonMap(sql, new Object[] {USERCODE, BPAMODULECODE, fromprocesscode}); 
					if(list != null && !list.isEmpty()) {
						map = list.get(0);
						map.setValue1(applicationcode);
					}
					response.put("nextProcess", map);
				}catch (Exception e) {
					response.put("nextProcess", map);
				}
			}else
				response.put("nextProcess", map);
			
		}catch (Exception e) {
			status = false;
		}
		return status;
	}
}
