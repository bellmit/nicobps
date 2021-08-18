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
import org.springframework.ui.Model;
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
	private  InitEnclosuresValidator  initEnclosuresValidator; 
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initmodulesenclosures.htm")
	public String initmodulesenclosures(Model model) {
		model.addAttribute("officeList", serviceUtilInterface.listUserOffices());
		model.addAttribute("licenseeTypeList", serviceUtilInterface.listLicenseeType());
		
		return "initialization/initmodulesenclosures";
	}
	@GetMapping(value = "/listModulesAndEnclosures.htm")
	public @ResponseBody List<Modules> listModulesAndEnclosures() {

		return serviceUserManagementInterface.listModulesAndEnclosures();
	}
	@PostMapping(value = "/saveModuleEnclosures.htm")
	public @ResponseBody String saveModuleEnclosures(@RequestBody List<Map<String, Object>> modulesenclosures) {
		String response="";
		if(modulesenclosures!=null) {
			String validate= initEnclosuresValidator.validateModulesEnclosure(modulesenclosures);
			if(validate!="")
				response=validate;
			else
				response = serviceUserManagementInterface.saveUserpages(modulesenclosures);
		}
		
		return response;
	}



	
}

