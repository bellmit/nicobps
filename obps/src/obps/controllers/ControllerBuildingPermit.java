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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.services.ServiceBPAInterface;
import obps.util.application.BPACalculatorConstants;
import obps.util.application.CommonMap;

@Controller
public class ControllerBuildingPermit {

	private static final Logger LOG = Logger.getLogger(ControllerBuildingPermit.class.toGenericString());
	private static final String PARENT_URL_MAPPING = "/bpa";
	private static final String COMPONENT_URL_MAPPING = "/bpa/components";
	private static final String REDIRECT_MAPPING = "redirect:";
	private static String appcode;
	private static String pathurl;
	private static Integer USERCODE;
	
	@Autowired
	private ServiceBPAInterface SBI;

	/* Page-URLs */
	@GetMapping(value = "/applybuildingpermit.htm")
	public String bpaApply(Model model, @RequestParam String edcrnumber) {
		LOG.info("URL: applyBuildingPermit.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("edcrnumber", edcrnumber);
			model.addAttribute("isalreadyapplied", SBI.checkIfBuildingPermitAlreadyApplied(USERCODE, edcrnumber)?1:0);
			return PARENT_URL_MAPPING.concat("/apply");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/buildingpermitsteptwo.htm")
	public String bpaApplyTwo(HttpServletRequest req, Model model, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: buildingpermitsteptwo.htm");
		HttpSession session = ControllerLogin.session();
		
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("buildingpermit.htm");
			
			appcode = applicationcode;
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkAccessGrantStatus(USERCODE, appcode, pathurl)?1:0);
			return PARENT_URL_MAPPING.concat("/buildingpermitsteptwo");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpapayappfee.htm")
	public String bpaPayAppFee(HttpServletRequest req, Model model, String applicationcode) {
		LOG.info("URL: bpapayappfee.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("buildingpermit.htm");
			
			appcode = applicationcode;
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkAccessGrantStatus(USERCODE, appcode, pathurl)?1:0);
			model.addAttribute("officecode", SBI.getApplicationOfficecode(applicationcode));
			return PARENT_URL_MAPPING.concat("/payappfee");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpapaypermfee.htm")
	public String bpaPayPermFee(HttpServletRequest req, Model model, String applicationcode) {
		LOG.info("URL: bpapaypermfee.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("buildingpermit.htm");
			
			appcode = applicationcode;
			pathurl = req.getServletPath();
			model.addAttribute("applicationcode", applicationcode);
			model.addAttribute("isactionallowed", SBI.checkAccessGrantStatus(USERCODE, appcode, pathurl)?1:0);
			return PARENT_URL_MAPPING.concat("/paypermfee");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/bpatrackstatus.htm")
	public String bpaTrackStatus(Model model, String applicationcode) {
		LOG.info("URL: bpatrackstatus.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/trackstatus");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}
	
	@GetMapping(value = "/buildingpermit.htm")
	public String buildingPermit(Model model) {
		LOG.info("URL: buildingpermit.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			return PARENT_URL_MAPPING.concat("/buildingpermit");
		}
		return REDIRECT_MAPPING.concat("login.htm");
	}
	
	/* COMPONENTS */
	@GetMapping(value = "/basicdetails.htm")
	public String basicDetails(Model model) {
		LOG.info("URL: basicdetails.htm");
		return COMPONENT_URL_MAPPING.concat("/basicdetails");
	}
	
	@GetMapping(value = "/bpaform.htm")
	public String basicDetailsForm(Model model) {
		LOG.info("URL: bpaform.htm");
		return COMPONENT_URL_MAPPING.concat("/bpaform");
	}
	
	@GetMapping(value = "/googlemap.htm")
	public String googleMap() {
		LOG.info("URL: googlemap.htm");
		return COMPONENT_URL_MAPPING.concat("/googlemap");
	}

	@GetMapping(value = "/modal.htm")
	public String modal() {
		LOG.info("URL: modal.htm");
		return COMPONENT_URL_MAPPING.concat("/modal");
	}

	/* GET */
	@GetMapping(value = "/getBpaApplicationDetails.htm")
	public @ResponseBody Map<String, Object> getBpaApplicationDetails(@RequestParam(name = "param") String applicationcode) {
		return SBI.getBpaApplicationDetails(USERCODE, applicationcode);	
	};
	
	@GetMapping(value = "/getBpaApplicationFee.htm")
	public @ResponseBody Map<String, Object> getApplicationFee(@RequestParam(name = "param") String applicationcode) {
		return SBI.getBPAFee(USERCODE, applicationcode, BPACalculatorConstants.APPLICATION_FEE_CODE);	
	};

	@GetMapping(value = "/getBpaPermitFee.htm")
	public @ResponseBody Map<String, Object> getBpaPermitFee(@RequestParam(name = "param") String applicationcode) {
		return SBI.getBPAFee(USERCODE, applicationcode, BPACalculatorConstants.PERMIT_FEE_CODE);	
	};
	
	@GetMapping(value = "/getEdcrDetails.htm")
	public @ResponseBody Map<String, Object> getEdcrDetails(@RequestParam(name = "param") String edcrnumber) {
		return SBI.getEdcrDetails(USERCODE, edcrnumber);
	};
	
	@GetMapping(value = "/getEdcrDetailsV2.htm")
	public @ResponseBody Map<String, Object> getEdcrDetailsByApplicationcode(@RequestParam(name = "param") String applicationcode) {
		return SBI.getEdcrDetailsV2(USERCODE, applicationcode);
	};
	
	@GetMapping(value = "/getOfficePaymentMode.htm")
	public @ResponseBody List<Map<String, Object>> getOfficePaymentMode(@RequestParam(name = "param") String applicationcode) {
		return SBI.listOfficePaymentMode(applicationcode);
	};

	@GetMapping(value = "/listAppScrutinyDetailsForBPA.htm")
	public @ResponseBody List<Map<String, Object>> listApplicationsScrutinyDetails() {
		return SBI.listAppScrutinyDetailsForBPA(USERCODE);
	};
	
	@GetMapping(value = "/listApplictionsCurrentProcessStatus.htm")
	public @ResponseBody List<Map<String, Object>> listApplictionsCurrentProcessStatus() {
		return SBI.listApplictionsCurrentProcessStatus(USERCODE);
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
	public ResponseEntity<HashMap<String, Object>> bpaMakePayment(@RequestBody BpaApplicationFee bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		if(SBI.processAppPayment(USERCODE, bpa, response))
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/savebpa.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPA(@RequestBody BpaApplication bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPA(bpa, USERCODE, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/savebpasteptwo.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPAStepTwo(@RequestBody BpaApplication bpa,
			@RequestParam(name = "processcode", required = false) Integer fromprocesscode) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPAStepTwo(bpa, USERCODE, fromprocesscode, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
