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
import obps.models.Pageurls;
import obps.models.Userlogin;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitEcnclosure {
	@Autowired

	private ServiceUtilInterface serviceUtilInterface;
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initenclosures.htm")
	public String initenclosures() {
		
		return "initialization/initenclosures";
	}

	@PostMapping(value = "/initenclosures.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initenclosures(@RequestBody Map<String, Object> enclosures) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		Long enclosurecode = getMaxEnclosureCode() +1;
		System.out.println("Enclosure Code"+enclosurecode);
		enclosures.put("enclosurecode", enclosurecode);
		
		if (serviceUserManagementInterface.initenclosures(enclosures)) {
			response.put("code", 201);
			response.put("data", 1);
		return ResponseEntity.ok().body(response);
	}
	response.put("response", HttpStatus.OK);
	response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/checkExistEnclosure.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> checkExistEnclosure(@RequestBody Map<String, Object> enclosures) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("CHECK");
		if (serviceUserManagementInterface.checkExistEnclosure(enclosures)) {
			response.put("response", HttpStatus.CREATED);
			response.put("data", 1);
		return ResponseEntity.ok().body(response);
	}
	response.put("response", HttpStatus.OK);
	response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}
	
	
	@PostMapping(value = "/updateinitenclosures.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateInitEnclosure(@RequestBody Enclosures enclosure) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (serviceUserManagementInterface.updateInitEnclosure(enclosure)) {
			response.put("code", 201);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	
	
	@GetMapping("/listEnclosures.htm")
	public @ResponseBody List<Enclosures> listEnclosures() {
		System.out.println("dasdasdasd");
		return serviceUserManagementInterface.listEnclosures();
	}


	private Long getMaxEnclosureCode() {
		String sql = "SELECT MAX(enclosurecode) FROM masters.enclosures";		
		return serviceUtilInterface.getMaxValue(sql);	
		
	}

	
}
