package obps.daos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import obps.models.BpaOwnerDetail;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Repository("BPADAO")
@Transactional
public class DaoBPA implements DaoBPAInterface{
	private static final Logger LOG = Logger.getLogger(DaoBPA.class.toGenericString());
	
	private final Integer BPAMODULECODE = 2;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ServiceUtilInterface SUI;
	
	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
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
			
			status = SUI.updateApplicationflowremarks(bpa.getApplicationcode(), BPAMODULECODE, 11, 12, USERCODE, null, "Apply for Building Buuilding");
			if(!status) throw new Exception("Error: Failed to update application flow");

			try {
				sql = "SELECT pageurl as key, processname as value  " + 
						"FROM masters.processflow PF    " + 
						"INNER JOIN masters.processes PR ON PR.processcode = PF.toprocesscode    " + 
						"INNER JOIN masters.pageurls PU ON PU.urlcode = PF.urlcode   " + 
						"WHERE PF.toprocesscode = 12";
				List<CommonMap> list = SUI.listCommonMap(sql); 
				if(list != null && !list.isEmpty()) {
					map = list.get(0);
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
	
	
}
