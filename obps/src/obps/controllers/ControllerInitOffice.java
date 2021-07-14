package obps.controllers;



import java.util.Map;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.util.application.ServiceUtilInterface;
import obps.util.common.Utilty;
import obps.validators.InitOfficesValidatorInterface;
import obps.daos.DaoEnclosureManagementInterface;
import obps.models.Enclosures;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.Userlogin;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceInitializationInterface;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitOffice {
	@Autowired 
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Autowired

	private ServiceInitializationInterface serviceInitalizationInterface;
	@Autowired
	private DaoEnclosureManagementInterface daoEnclosureManagementInterface;
	@Autowired

	private InitOfficesValidatorInterface initOfficesValidatorInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initoffices.htm")
	public String initoffices() {
		
		
		return "initialization/initoffices";
	}

	@PostMapping(value = "/initoffices.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initoffices(@RequestBody Map<String, Object> offices) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(offices!=null) {
			System.out.println(offices);
			String validate= initOfficesValidatorInterface.validateInitOffices(offices);
			System.out.println("validate"+validate);
			if(validate!="") {
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			Long officecode=(long) 0;
			if(getMaxOfficeCode()>0)
				officecode = getMaxOfficeCode() +1;
			else
				officecode = officecode+1;
			System.out.println("Enclosure Code"+officecode);
			offices.put("officecode", officecode);
			
			String officename1=((String) offices.get("officename1")).trim();
			String officename2=((String) offices.get("officename2")).trim();
			String officename3="";
			if(offices.get("officename3")!=null)
			officename3=((String) offices.get("officename3")).trim();
			String sql = "Select count(*) from  masters.offices where LOWER(officename1)=LOWER(?) AND LOWER(officename2)=LOWER(?) AND LOWER(officename3)=LOWER(?)";
			Object[] values = {officename1,officename2,officename3 };
			boolean exist = daoEnclosureManagementInterface.checkExistance(sql, values);
			System.out.println(exist);
			if(exist)
				response.put("data", "exist");
			else
			{
				
				
				
		if (serviceUserManagementInterface.initoffices(offices)) {
					
					response.put("data", "Success");
				}
				else
					response.put("data", "Error");
			}
		}
		
		
	
		System.out.println(response);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/updateinitoffices.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateinitoffices(@RequestBody Map<String, Object> offices) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(offices!=null) {
			String validate= initOfficesValidatorInterface.validateInitOffices(offices);
			System.out.println("validate"+validate);
			if(validate!="") {
				response.put("data", validate);
				return ResponseEntity.ok().body(response);
			}
			
			if (serviceUserManagementInterface.updateinitoffices(offices)) {
				response.put("data", "Success");
				return ResponseEntity.ok().body(response);
			}
			else
				response.put("data", "Error");
		}
		
		
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listOffices.htm")
	public @ResponseBody List<Offices> listOffices() {
		return serviceUserManagementInterface.listOffices();
	}

	private Long getMaxOfficeCode() {
		String sql = "SELECT MAX(officecode) FROM masters.offices";		
		return serviceUtilInterface.getMaxValue(sql);	
		
	}
	@RequestMapping(value = "/showFile.htm", method = RequestMethod.POST)
	public @ResponseBody String showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "officecode", required = true) String officecode
			) {
		
		byte[] binaryFile=null;
		try {
			String sql = "select logo from masters.offices where officecode=?";
			Object[] criteria = { Integer.valueOf(officecode)};
			binaryFile = serviceUtilInterface.getBytes(sql, criteria);
			System.out.println(binaryFile);

		} catch (Exception e) {
			System.out.println("Exception :: " + e);
			
		}
		return (binaryFile != null) ? Base64.getEncoder().encodeToString(binaryFile) : null;
	}


	
}
