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
import obps.models.Enclosures;
import obps.models.Modules;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.Userlogin;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitModulesEnclosures {
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initmodulesenclosures.htm")
	public String initmodulesenclosures() {
		
		
		return "initialization/initmodulesenclosures";
	}
	@GetMapping(value = "/listModulesAndEnclosures.htm")
	public @ResponseBody List<Modules> listModulesAndEnclosures() {

		return serviceUserManagementInterface.listModulesAndEnclosures();
	}
	@PostMapping(value = "/saveModuleEnclosures.htm")
	public @ResponseBody String saveModuleEnclosures(@RequestBody List<Map<String, Object>> modulesenclosures) {

		return serviceUserManagementInterface.saveUserpages(modulesenclosures);
	}

//	@PostMapping(value = "/initoffices.htm", consumes = "application/json")
//	public ResponseEntity<HashMap<String, Object>> initoffices(@RequestBody Map<String, Object> offices) {
//		HashMap<String, Object> response = new HashMap<String, Object>();
//		Long officecode = getMaxOfficeCode() +1;
//		System.out.println("Enclosure Code"+officecode);
//		offices.put("officecode", officecode);
//		
//		if (serviceUserManagementInterface.initoffices(offices)) {
//			response.put("response", HttpStatus.CREATED);
//			response.put("data", 1);
//		return ResponseEntity.ok().body(response);
//	}
//	response.put("response", HttpStatus.OK);
//	response.put("data", -1);
//		return ResponseEntity.ok().body(response);
//	}
//	
//	@PostMapping(value = "/updateinitoffices.htm", consumes = "application/json")
//	public ResponseEntity<HashMap<String, Object>> updateinitoffices(@RequestBody Offices offices) {
//		HashMap<String, Object> response = new HashMap<String, Object>();
//
//		if (serviceUserManagementInterface.updateinitoffices(offices)) {
//			response.put("response", HttpStatus.CREATED);
//			response.put("data", 1);
//			return ResponseEntity.ok().body(response);
//		}
//		response.put("response", HttpStatus.OK);
//		response.put("data", -1);
//		return ResponseEntity.ok().body(response);
//	}
//
//	@GetMapping("/listOffices.htm")
//	public @ResponseBody List<Offices> listOffices() {
//		
//		System.out.println("dasdasdasd11111");
//		return serviceUserManagementInterface.listOffices();
//	}
//
//	private Long getMaxOfficeCode() {
//		String sql = "SELECT MAX(officecode) FROM masters.offices";		
//		return serviceUtilInterface.getMaxValue(sql);	
//		
//	}

	
}

