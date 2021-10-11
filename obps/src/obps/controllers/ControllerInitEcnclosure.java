package obps.controllers;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.util.application.ServiceUtilInterface;
import obps.util.common.Utilty;
import obps.validators.InitEnclosuresValidator;

import obps.daos.DaoEnclosureManagementInterface;
import obps.models.Enclosures;
import obps.models.Filetypes;
import obps.models.Pageurls;
import obps.models.Userlogin;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceInitializationInterface;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitEcnclosure { 
	@Autowired

	private ServiceUtilInterface serviceUtilInterface;
	@Autowired

	private InitEnclosuresValidator initEnclosuresValidator;
	
	@Autowired
	private DaoEnclosureManagementInterface daoEnclosureManagementInterface;
	
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Autowired

	private ServiceInitializationInterface serviceInitalizationInterface;
	@Resource
	private Environment environment;

	private JdbcTemplate jdbcTemplate;
	@GetMapping("/initenclosures.htm")
	public String initenclosures() {
		
		return "initialization/initenclosures";
	}

	@PostMapping(value = "/initenclosures.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initenclosures(@RequestBody Map<String, Object> enclosures) {
		
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(enclosures!=null) {
			String validate= initEnclosuresValidator.validateInitEnclosure(enclosures);
			if(validate!="") {
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			Long enclosurecode=(long) 0;
			if(getMaxEnclosureCode()>0)
				enclosurecode = getMaxEnclosureCode() +1;
			else
				enclosurecode = enclosurecode+1;
			System.out.println("Enclosure Code"+enclosurecode);
			enclosures.put("enclosurecode", enclosurecode);
			String encl_name=((String) enclosures.get("enclosurename")).trim();
			String sql = "Select count(*) from  masters.enclosures where LOWER(enclosurename)=LOWER(?)";
			Object[] values = {encl_name};
			boolean exist = daoEnclosureManagementInterface.checkExistance(sql, values);
			System.out.println("easdasd"+exist);
			if(exist)
				response.put("data", "exist");
			else {
				
					
				if (serviceUserManagementInterface.initenclosures(enclosures)) {
		
					response.put("data", "Success");
				}
				else
					response.put("data", "Error");
			}
		}
		
			
	System.out.println(response.get("data"));
		return ResponseEntity.ok().body(response);
	}
	
		
	
	@PostMapping(value = "/updateinitenclosures.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateInitEnclosure(@RequestBody Map<String, Object> enclosures) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(enclosures!=null) {
			String encl_name=((String) enclosures.get("enclosuredescription")).trim();
			
			String sql = "Select * from  masters.enclosures where LOWER(enclosurename)=LOWER(?)";
			Object[] values = {encl_name};
//			boolean exist = serviceInitalizationInterface.checkExistance(sql, values);
//			if(exist)
//				response.put("data", "exist");
//			else {
				String validate= initEnclosuresValidator.validateInitEnclosure(enclosures);
				if(validate!="") {
					response.put("data", validate);
					return ResponseEntity.ok().body(response);
				}
					
				if (serviceUserManagementInterface.updateInitEnclosure(enclosures)) {
//					response = "Success";	
					response.put("data", "Success");
				}
				else
					response.put("data", "Error");
//			}
		}
		
			
	System.out.println(response.get("data"));
		return ResponseEntity.ok().body(response);
		
		
		
		
	}

	
	
	@GetMapping("/listEnclosures.htm")
	public @ResponseBody List<Enclosures> listEnclosures() {
		System.out.println("dasdasdasd");
		return serviceUserManagementInterface.listEnclosures();
	}
	
	@GetMapping("/listFiletypes.htm")
	public @ResponseBody List<Filetypes> listFiletypes() {
		System.out.println("listFiletypes");
		
		return serviceUserManagementInterface.listFiletypes();
	}


	private Long getMaxEnclosureCode() {
		String sql = "SELECT MAX(enclosurecode) FROM masters.enclosures";		
		return serviceUtilInterface.getMaxValue(sql);	
		
	}

	
}
