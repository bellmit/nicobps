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
import obps.validators.InitOfficesValidatorInterface;
import obps.models.Enclosures;
import obps.models.Modules;
import obps.models.Offices;
import obps.models.Pageurls;
import obps.models.PaymentModes;
import obps.models.Userlogin;
import obps.services.ServiceEnclosureManagementInterface;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerInitOfficesPaymentModes {
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired

	private InitOfficesValidatorInterface initOfficesValidatorInterface;
	@Autowired

	private ServiceEnclosureManagementInterface serviceUserManagementInterface;
	@Resource
	private Environment environment;

	
	@GetMapping("/initofficespaymentmodes.htm")
	public String initofficespaymentmodes() {
		
		
		return "initialization/initofficespaymentmodes";
	}
	
	@GetMapping("/listPaymentModes.htm")
	public @ResponseBody List<PaymentModes> listPaymentModes() {
		System.out.println("dasdasdasd");
		return serviceUserManagementInterface.listPaymentModes();
	}
	@GetMapping(value = "/listOfficesAndPaymentModes.htm")
	public @ResponseBody List<Offices> listOfficesAndPaymentModes() {

		return serviceUserManagementInterface.listOfficesAndPaymentModes();
	}

	@PostMapping(value = "/saveOfficePayment.htm")
	public @ResponseBody String saveOfficePayment(@RequestBody List<Map<String, Object>> officespayments) {
		String response="";
		if(officespayments!=null) {
			String validate= initOfficesValidatorInterface.validateOfficesPaymentModes(officespayments);
			if(validate!="")
				response=validate;
			else
				response = serviceUserManagementInterface.saveOfficePayment(officespayments);
		}
		
		return response;
		 
	}



	
}

