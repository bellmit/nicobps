package obps.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.ServiceStakeholderInterface;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerStakeholder {
	@Autowired private ServiceStakeholderInterface SSI;
	@Autowired private ServiceUtilInterface serviceUtilInterface;  
	
	@GetMapping("/srverify.htm")
	public String verification() {
		return "stakeholder/srverify";
	}
	
	@GetMapping("/srapproval.htm")
	public String approval() {
		return "stakeholder/srverify";
	}
	
	@PostMapping("/listLicensees.htm")
	public @ResponseBody List<Map<String,Object>> listLicensees() {
		return SSI.listLicensees();
	}

	@PostMapping("/listEnclosures.htm")
	public @ResponseBody List<CommonMap> listEnclosures() {
		return serviceUtilInterface.listEnclosures(Short.valueOf("1"));
	}

	@PostMapping("/getEnclosure.htm")
	public @ResponseBody String getEnclosures(Integer usercode,Integer enclosurecode) {
		byte[] file=SSI.getEnclosure(usercode,enclosurecode);
		return (file!=null)?Base64.getEncoder().encodeToString(file):null;		
	}
}
