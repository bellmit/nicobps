/*@author Decent Khongstia*/
package obps.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.BpaApplication;
import obps.services.ServiceBPAInterface;
import obps.util.application.CommonMap;

@Controller
public class ControllerBuildingPermit {
	
	private static final Logger LOG = Logger.getLogger(ControllerBuildingPermit.class.toGenericString());
	private static final String PARENT_URL_MAPPING = "/bpa";
	private static Integer USERCODE;
	
	@Autowired
	private ServiceBPAInterface SBI;
	
	
	/* Page-URLs */
	@GetMapping(value = "/applybuildingpermit.htm")
	public String applyBuildingPermit(Model model, @RequestParam String edcrnumber) {
		LOG.info("URL: applyBuildingPermit.htm");
		HttpSession session = ControllerLogin.session();
		if(session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("edcrnumber", edcrnumber);
			return PARENT_URL_MAPPING.concat("/apply") ;
		}
		
		return "redirect:login.htm";
	}

	@GetMapping(value = "/bpaform.htm")
	public String basicDetailsForm(Model model) {
		LOG.info("URL: bpaform.htm");
		return PARENT_URL_MAPPING.concat("/bpaform") ;
	}
	
	@GetMapping(value = "/buildingpermit.htm")
	public String buildingPermit(Model model) {
		LOG.info("URL: buildingpermit.htm");
		HttpSession session = ControllerLogin.session();
		if(session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			return PARENT_URL_MAPPING.concat("/buildingpermit") ;
		}
		
		return "redirect:login.htm";
	}

	@GetMapping(value = "/buildingpermitsteptwo.htm")
	public String buildingPermitStepTwo(Model model, @RequestParam String applicationcode) {
		LOG.info("URL: buildingpermitsteptwo.htm");
		HttpSession session = ControllerLogin.session();
		if(session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/buildingpermitsteptwo") ;
		}
		
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/googlemap.htm")
	public String googleMap() {
		LOG.info("URL: googlemap.htm");
		return PARENT_URL_MAPPING.concat("/googlemap") ;
	}
	
	/* GET */
	@GetMapping(value = "/getEdcrDetails.htm")
	public @ResponseBody Map<String, Object> getEdcrDetails(@RequestParam(name = "param") String edcrnumber) {
		return SBI.getEdcrDetails(USERCODE, edcrnumber);
	};
	
	@GetMapping(value = "/listAppScrutinyDetailsForBPA.htm")
	public @ResponseBody List<Map<String, Object>> listApplicationsScrutinyDetails() {
		return SBI.listAppScrutinyDetailsForBPA(USERCODE);
	};

	@GetMapping(value = "/listOfficelocations.htm")
	public @ResponseBody List<CommonMap> listOfficelocations(@RequestParam(name = "param", required = false) Integer officecode) {
		if(officecode != null)
			return SBI.listOfficelocations(officecode);
		return SBI.listOfficelocations();
	}
	
	@GetMapping(value = "/listOwnershiptypes.htm")
	public @ResponseBody List<CommonMap> listOwnershiptypes() {
		return SBI.listOwnershiptypes();
	}

	@GetMapping(value = "/listRelationshiptypes.htm")
	public @ResponseBody List<CommonMap> listRelationshiptypes() {
		return SBI.listRelationshiptypes();
	}

	@GetMapping(value = "/listSalutations.htm")
	public @ResponseBody List<CommonMap> listSalutations() {
		return SBI.listSalutations();
	}
	
	/* CREATE */
	@PostMapping(value = "/savebpa.htm")
	public ResponseEntity<HashMap<String, Object>> createlicenseesregistrationsm(@RequestBody BpaApplication bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if(USERCODE == null) return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		
		if(SBI.saveBPA(bpa, USERCODE, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
