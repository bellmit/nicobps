package obps.daos;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import obps.util.application.DaoUtilInterface;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.Patterns;
import obps.util.common.UtilFile;

@Transactional
@Repository("daoUploadBpaEnclosuers")
public class DaoUploadBpaEnclosuers implements DaoUploadBpaEnclosuersInterface {

	

	private static final Logger LOG = Logger.getLogger(DaoBPA.class.toGenericString());
	private final Integer BPAMODULECODE = 2;
	private final Integer BPACURRENTPROCESSCODE = 3;
	private final Integer BPANEXTPROCESSCODE = 4;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private DaoUtilInterface daoUtilInterface;
	@Autowired
	private ServiceUtilInterface SUI;

	@Autowired
	public void createTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	//validate enclosusres
	@Override
	public boolean validateEnclosureDetails(Map<String, Object> param) {
//		Environment environment = null;
//	
//		maxfilesize = environment.getRequiredProperty("enclosure.max-file-size");
		System.out.println("dao validate file::"+param);
		boolean response = false;
		String sql = null;
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse((String) param.get("listEnclosures"));
			JSONArray listEnclosures = (JSONArray) obj;
			System.out.println("listEnclosures::"+listEnclosures);
//			Object obj1 = parser.parse((String) param.get("applicationcode"));
//			String applicationcode = obj1.toString();
//			Integer appenclosurecode = Integer.valueOf((String) param.get("appenclosurecode"));
//			Integer usercode = Integer.valueOf((String) param.get("usercode"));
//			int fromprocesscode = 0;
//			int toprocesscode = 0;

			if (listEnclosures.size() > 0) {
				
				for (int i = 0; i < listEnclosures.size(); i++) {
					JSONObject row = (JSONObject) listEnclosures.get(i);
					Boolean ischecked = (Boolean) row.get("ischecked");
					if (ischecked) {
						byte[] file = Base64.getDecoder().decode(((String) row.get("filecontant")).split(",")[1]);
						
						if(UtilFile.isValidFile(file, Patterns.PATTERN_PDF_CONTANT)||UtilFile.isValidFile(file, Patterns.PATTERN_IMAGE_CONTANT)) {
							response = true;
						}
						
						
					}
				}
				
					if (!response)
						throw new Exception("Error: Failed to validate file");
				}
			
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUserManagement.validateEnclosureDetails(Map<String,String> param) : " + e);
		}
		System.out.println("response in DaoValidateEnclosuers == " + response);
		return response;
	
	}
//	@Override
//	public boolean submitBpaEnclosureDetails(Map<String, Object> param) {
//		boolean response = false;
//		String sql = null;
//		try {
//			JSONParser parser = new JSONParser();
//			Object obj = parser.parse((String) param.get("listBpaEnclosures"));
//			JSONArray listBpaEnclosures = (JSONArray) obj;
//			Object obj1 = parser.parse((String) param.get("applicationcode"));
//			String applicationcode = obj1.toString();
//			System.out.println("applicationcode === "+applicationcode);
//			Integer appenclosurecode = Integer.valueOf((String) param.get("appenclosurecode"));
//			Integer usercode = Integer.valueOf((String) param.get("usercode"));
//			int fromprocesscode = 0;
//			int toprocesscode = 0;
//
//			if (listBpaEnclosures.size() > 0) {
//				sql = "DELETE FROM  nicobps.bpaenclosures WHERE applicationcode=?";
//				Object[] values = { applicationcode };
//				jdbcTemplate.update(sql, values);
//				sql = "INSERT INTO nicobps.bpaenclosures(appenclosurecode,applicationcode,enclosurecode,enclosureimage) VALUES(?,?,?,?)";
//				List<Object[]> list = new ArrayList<>();
//				for (int i = 0; i < listBpaEnclosures.size(); i++) {
//					JSONObject row = (JSONObject) listBpaEnclosures.get(i);
//					Boolean ischecked = (Boolean) row.get("ischecked");
//					if (ischecked) {
//						Short enclosurecode = Short.valueOf((String) row.get("enclosurecode"));
//						byte[] file = Base64.getDecoder().decode(((String) row.get("filecontant")).split(",")[1]);
//						list.add(new Object[] { appenclosurecode, applicationcode, enclosurecode, file });
//					}
//					appenclosurecode = appenclosurecode + 1;
//				}
//				response = jdbcTemplate.batchUpdate(sql, list).length > 0;
//				if (response) {
//					Integer fromusercode = usercode;
//					List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
//					Map<String, Object> tmap = new HashMap<String, Object>();
//					tlist = SUI.getCurrentProcessStatus(BPAMODULECODE, applicationcode);
//					if (tlist != null && !tlist.isEmpty()) {
//						tmap = tlist.get(0);
//						fromprocesscode = (Integer) tmap.get("fromprocesscode");
//						toprocesscode = (Integer) tmap.get("toprocesscode");
//					}
//					
//					response = SUI.updateApplicationflowremarks(applicationcode, BPAMODULECODE, fromprocesscode,toprocesscode, usercode, null, "Application Submitted");
//					if (!response)
//						throw new Exception("Error: Failed to update application	 flow");
//				}
//			}
//		} catch (Exception e) {
//			e.getStackTrace();
//			response = false;
//			System.out.println("Error in DaoUserManagement.submitEnclosureDetails(Map<String,String> param) : " + e);
//		}
//		System.out.println("response in DaoUploadBpaEnclosuers == " + response);
//		return response;
//	}
	@Override
	public boolean submitBpaEnclosureDetails(Map<String, Object> param) {
		boolean response = false;
		String sql = null;
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse((String) param.get("listBpaEnclosures"));
			JSONArray listBpaEnclosures = (JSONArray) obj;
			Object obj1 = parser.parse((String) param.get("applicationcode"));
			String applicationcode = obj1.toString();
			Integer appenclosurecode = Integer.valueOf((String) param.get("appenclosurecode"));
			Integer usercode = Integer.valueOf((String) param.get("usercode"));
			int fromprocesscode = 0;
			int toprocesscode = 0;

			if (listBpaEnclosures.size() > 0) {
				sql = "DELETE FROM  nicobps.bpaenclosures WHERE applicationcode=?";
				Object[] values = { applicationcode };
				jdbcTemplate.update(sql, values);
				sql = "INSERT INTO nicobps.bpaenclosures(appenclosurecode,applicationcode,enclosurecode,enclosureimage) VALUES(?,?,?,?)";
				List<Object[]> list = new ArrayList<>();
				for (int i = 0; i < listBpaEnclosures.size(); i++) {
					JSONObject row = (JSONObject) listBpaEnclosures.get(i);
					Boolean ischecked = (Boolean) row.get("ischecked");
					if (ischecked) {
						Short enclosurecode = Short.valueOf((String) row.get("enclosurecode"));
						byte[] file = Base64.getDecoder().decode(((String) row.get("filecontant")).split(",")[1]);
						list.add(new Object[] { appenclosurecode, applicationcode, enclosurecode, file });
					}
					appenclosurecode = appenclosurecode + 1;
				}
				response = jdbcTemplate.batchUpdate(sql, list).length > 0;
				if (response) {
					Integer fromusercode = usercode;
					List<Map<String, Object>> tlist = new ArrayList<Map<String, Object>>();
					Map<String, Object> tmap = new HashMap<String, Object>();
					tlist = SUI.getCurrentProcessStatus(BPAMODULECODE, applicationcode);
					if (tlist != null && !tlist.isEmpty()) {
						tmap = tlist.get(0);
						fromprocesscode = (Integer) tmap.get("fromprocesscode");
						toprocesscode = (Integer) tmap.get("toprocesscode");
					}
					
					response = SUI.updateApplicationflowremarks(applicationcode, BPAMODULECODE, fromprocesscode,toprocesscode, usercode, null, "Application Submitted");
					if (!response)
						throw new Exception("Error: Failed to update application	 flow");
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
			response = false;
			System.out.println("Error in DaoUploadBpaEnclosuers.submitBpaEnclosureDetails(Map<String,String> param) : " + e);
		}
		System.out.println("response in DaoUploadBpaEnclosuers == " + response);
		return response;
	}

}
