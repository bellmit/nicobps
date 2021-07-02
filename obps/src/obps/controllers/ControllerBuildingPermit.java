/*@author Decent Khongstia*/
package obps.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.services.ServiceBPAInterface;
import obps.util.application.BPAConstants;
import obps.util.application.CommonMap;

@Controller
@SessionAttributes({ "SESSION_USERCODE" })
public class ControllerBuildingPermit {

	private static final Logger LOG = Logger.getLogger(ControllerBuildingPermit.class.toGenericString());

	private static String pathurl;
	
	@Autowired
	private ServiceBPAInterface SBI;

	@ModelAttribute("SESSION_USERCODE")
	Integer getSessionUsercode() {
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			return Integer.valueOf(session.getAttribute("usercode").toString());
		}
		return null;
	}
	
	/* Page-URLs */
	@GetMapping(value = "/applybuildingpermit.htm")
	public String bpaApply(Model model, @ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam String edcrnumber) {
		LOG.info("URL: applyBuildingPermit.htm");
		if (usercode != null && usercode > -1) {
			model.addAttribute("edcrnumber", edcrnumber);
			model.addAttribute("isalreadyapplied", SBI.checkIfBuildingPermitAlreadyApplied(usercode, edcrnumber)?1:0);
			return BPAConstants.PARENT_URL_MAPPING.concat("/apply");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/buildingpermitsteptwo.htm")
	public String bpaApplyTwo(HttpServletRequest req, Model model, @ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: buildingpermitsteptwo.htm");
		if (usercode != null && usercode > -1) {
			if(applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("buildingpermit.htm");
			
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkPageAccessGrantStatus(usercode, applicationcode, pathurl)?1:0);
			return BPAConstants.PARENT_URL_MAPPING.concat("/buildingpermitsteptwo");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpapayappfee.htm")
	public String bpaPayAppFee(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam String applicationcode) {
		LOG.info("URL: bpapayappfee.htm");
		if (usercode != null && usercode > -1) {
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkPageAccessGrantStatus(usercode, applicationcode, pathurl)?1:0);
			model.addAttribute("officecode", SBI.getApplicationOfficecode(applicationcode));
			return BPAConstants.PARENT_URL_MAPPING.concat("/payappfee");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpapaypermfee.htm")
	public String bpaPayPermFee(HttpServletRequest req, Model model, @ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam String applicationcode) {
		LOG.info("URL: bpapaypermfee.htm");
		if (usercode != null && usercode > -1) {
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkPageAccessGrantStatus(usercode, applicationcode, pathurl)?1:0);
			return BPAConstants.PARENT_URL_MAPPING.concat("/paypermfee");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpatrackstatus.htm")
	public String bpaTrackStatus(Model model, @ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: bpatrackstatus.htm");
		if (usercode != null && usercode > -1) {
			model.addAttribute("applicationcode", applicationcode);
			return BPAConstants.PARENT_URL_MAPPING.concat("/trackstatus");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/buildingpermit.htm")
	public String buildingPermit(Model model, @ModelAttribute("SESSION_USERCODE") Integer usercode) {
		LOG.info("URL: buildingpermit.htm");
		if (usercode != null && usercode > -1) {
			return BPAConstants.PARENT_URL_MAPPING.concat("/buildingpermit");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}
	
	/* COMPONENTS */
	@GetMapping(value = "/basicdetails.htm")
	public String basicDetails(Model model) {
		LOG.info("URL: basicdetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/basicdetails");
	}
	
	@GetMapping(value = "/bpaform.htm")
	public String basicDetailsForm(Model model) {
		LOG.info("URL: bpaform.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/bpaform");
	}
	
	@GetMapping(value = "/googlemap.htm")
	public String googleMap() {
		LOG.info("URL: googlemap.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/googlemap");
	}

	@GetMapping(value = "/modal.htm")
	public String modal() {
		LOG.info("URL: modal.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/modal");
	}

	/* GET */
	@GetMapping(value = "/getBpaApplicationDetails.htm")
	public @ResponseBody Map<String, Object> getBpaApplicationDetails(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String applicationcode) {
		return SBI.getBpaApplicationDetails(usercode, applicationcode);	
	};
	
	@GetMapping(value = "/getBpaApplicationFee.htm")
	public @ResponseBody Map<String, Object> getApplicationFee(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String applicationcode) {
		return SBI.getBPAFee(usercode, applicationcode, BPAConstants.APPLICATION_FEE_CODE);	
	};

	@GetMapping(value = "/getBpaPermitFee.htm")
	public @ResponseBody Map<String, Object> getBpaPermitFee(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String applicationcode) {
		return SBI.getBPAFee(usercode, applicationcode, BPAConstants.PERMIT_FEE_CODE);	
	};
	
	@GetMapping(value = "/getEdcrDetails.htm")
	public @ResponseBody Map<String, Object> getEdcrDetails(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String edcrnumber) {
		return SBI.getEdcrDetails(usercode, edcrnumber);
	};
	
	@GetMapping(value = "/getEdcrDetailsV2.htm")
	public @ResponseBody Map<String, Object> getEdcrDetailsByApplicationcode(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String applicationcode) {
		return SBI.getEdcrDetailsV2(usercode, applicationcode);
	};
	
	@GetMapping(value = "/getOfficePaymentMode.htm")
	public @ResponseBody List<Map<String, Object>> getOfficePaymentMode(@RequestParam(name = "param") String applicationcode) {
		return SBI.listOfficePaymentMode(applicationcode);
	};

	@GetMapping(value = "/listAppScrutinyDetailsForBPA.htm")
	public @ResponseBody List<Map<String, Object>> listApplicationsScrutinyDetails(@ModelAttribute("SESSION_USERCODE") Integer usercode) {
		return SBI.listAppScrutinyDetailsForBPA(usercode);
	};
	
	@GetMapping(value = "/listApplictionsCurrentProcessStatus.htm")
	public @ResponseBody List<Map<String, Object>> listApplictionsCurrentProcessStatus(@ModelAttribute("SESSION_USERCODE") Integer usercode) {
		return SBI.listApplictionsCurrentProcessStatus(usercode);
	};

	@GetMapping(value = "/listNextProcess.htm")
	public @ResponseBody List<Map<String, Object>> listNextProcess(@RequestParam String applicationcode) {
		return SBI.listNextProcess(applicationcode);
	}

	@GetMapping(value = "/listOfficelocations.htm")
	public @ResponseBody List<CommonMap> listOfficelocations(
			@RequestParam(name = "param", required = false) Integer officecode) {
		if (officecode != null)
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
	/*
	 * @PostMapping("/bpapayappfee.htm") public ResponseEntity<HashMap<String,
	 * Object>> bpaPayAppFee(@RequestBody BpaApplicationFee bpa) { HashMap<String,
	 * Object> response = new HashMap<String, Object>(); LOG.info(bpa.toString());
	 * bpa.setFeetypecode(BPA_APPLICATIONFEE_CODE); if (USERCODE == null) return new
	 * ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	 * if(SBI.processAppPayment(USERCODE, bpa, response)) return new
	 * ResponseEntity<>(response, HttpStatus.CREATED); else return new
	 * ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); }
	 * 
	 * @PostMapping("/bpapaypermfee.htm") public ResponseEntity<HashMap<String,
	 * Object>> bpaPayPermFee(@RequestBody BpaApplicationFee bpa) { HashMap<String,
	 * Object> response = new HashMap<String, Object>(); LOG.info(bpa.toString());
	 * bpa.setFeetypecode(BPA_PERMITFEE_CODE); if (USERCODE == null) return new
	 * ResponseEntity<>(response, HttpStatus.FORBIDDEN);
	 * if(SBI.processAppPayment(USERCODE, bpa, response)) return new
	 * ResponseEntity<>(response, HttpStatus.CREATED); else return new
	 * ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); }
	 */
	
	@PostMapping("/bpamakepayment.htm")
	public ResponseEntity<HashMap<String, Object>> bpaMakePayment(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaApplicationFee bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		if(SBI.processAppPayment(usercode, bpa, response))
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/savebpa.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPA(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaApplication bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPA(bpa, usercode, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/savebpasteptwo.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPAStepTwo(@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaApplication bpa,
			@RequestParam(name = "processcode", required = false) Integer fromprocesscode) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPAStepTwo(bpa, usercode, fromprocesscode, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
