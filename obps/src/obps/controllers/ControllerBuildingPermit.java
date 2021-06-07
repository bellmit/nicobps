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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.BpaApplication;
import obps.models.BpaApplicationFee;
import obps.models.BpaSiteInspection;
import obps.services.ServiceBPAInterface;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerBuildingPermit {

	private static final Logger LOG = Logger.getLogger(ControllerBuildingPermit.class.toGenericString());
	private static final String PARENT_URL_MAPPING = "/bpa";
	private static Integer USERCODE;
	private static final Integer BPA_APPLICATIONFEE_CODE = 5;
	private static final Integer BPA_PERMITFEE_CODE = 6;
	
	@Autowired
	private ServiceBPAInterface SBI;
	@Autowired
	private ServiceUtilInterface SUI;

	/* Page-URLs */
	@GetMapping(value = "/bpaform.htm")
	public String basicDetailsForm(Model model) {
		LOG.info("URL: bpaform.htm");
		return PARENT_URL_MAPPING.concat("/bpaform");
	}

	@GetMapping(value = "/applybuildingpermit.htm")
	public String bpaApply(Model model, @RequestParam String edcrnumber) {
		LOG.info("URL: applyBuildingPermit.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("edcrnumber", edcrnumber);
			return PARENT_URL_MAPPING.concat("/apply");
		}
		return "redirect:login.htm";
	}

	@GetMapping(value = "/buildingpermitsteptwo.htm")
	public String bpaApplyTwo(Model model, @RequestParam String applicationcode) {
		LOG.info("URL: buildingpermitsteptwo.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/buildingpermitsteptwo");
		}
		return "redirect:login.htm";
	}

	@GetMapping(value = "/bpaapproval.htm")
	public String bpaApproval(Model model, @RequestParam String applicationcode) {
		LOG.info("URL: bpaapproval.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/approval");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpapayappfee.htm")
	public String bpaPayAppFee(Model model, String applicationcode) {
		LOG.info("URL: bpapayappfee.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/payappfee");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpapaypermfee.htm")
	public String bpaPayPermFee(Model model, String applicationcode) {
		LOG.info("URL: bpapaypermfee.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/paypermfee");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpasiteinspection.htm")
	public String bpaSiteInspection(Model model, @RequestParam String applicationcode) {
		LOG.info("URL: applybpsiteinspection.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			return PARENT_URL_MAPPING.concat("/siteinspection");
		}
		return "redirect:login.htm";
	}

	@GetMapping(value = "/buildingpermit.htm")
	public String buildingPermit(Model model) {
		LOG.info("URL: buildingpermit.htm");
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			return PARENT_URL_MAPPING.concat("/buildingpermit");
		}
		return "redirect:login.htm";
	}

	@GetMapping(value = "/googlemap.htm")
	public String googleMap() {
		LOG.info("URL: googlemap.htm");
		return PARENT_URL_MAPPING.concat("/googlemap");
	}

	@GetMapping(value = "/modal.htm")
	public String modal(Model model, String modalTitle) {
		LOG.info("URL: modal.htm");
		model.addAttribute("modalTitle", modalTitle);
		return PARENT_URL_MAPPING.concat("/rejectmodal");
	}

	/* GET */
	@GetMapping(value = "/getBpaApplicationFee.htm")
	public @ResponseBody Map<String, Object> getApplicationFee(@RequestParam(name = "param") String applicationcode) {
		return SBI.getApplicationFee(USERCODE, applicationcode, BPA_APPLICATIONFEE_CODE);	
	};

	@GetMapping(value = "/getBpaPermitFee.htm")
	public @ResponseBody Map<String, Object> getBpaPermitFee(@RequestParam(name = "param") String applicationcode) {
		return SBI.getPermitFee(USERCODE, applicationcode, BPA_PERMITFEE_CODE);	
	};

	@GetMapping(value = "/getEdcrDetails.htm")
	public @ResponseBody Map<String, Object> getEdcrDetails(@RequestParam(name = "param") String edcrnumber) {
		return SBI.getEdcrDetails(USERCODE, edcrnumber);
	};

	@GetMapping(value = "/getOfficePaymentMode.htm")
	public @ResponseBody List<Map<String, Object>> getOfficePaymentMode(@RequestParam(name = "param") String applicationcode) {
		return SBI.listOfficePaymentMode(applicationcode);
	};

	@GetMapping(value = "/listAppScrutinyDetailsForBPA.htm")
	public @ResponseBody List<Map<String, Object>> listApplicationsScrutinyDetails() {
		return SBI.listAppScrutinyDetailsForBPA(USERCODE);
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
	@PostMapping("/bpapayappfee.htm")
	public ResponseEntity<HashMap<String, Object>> bpaPayAppFee(@RequestBody BpaApplicationFee bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		bpa.setFeetypecode(BPA_APPLICATIONFEE_CODE);
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		if(SBI.processAppPayment(USERCODE, bpa, response))
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/bpapaypermfee.htm")
	public ResponseEntity<HashMap<String, Object>> bpaPayPermFee(@RequestBody BpaApplicationFee bpa) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		LOG.info(bpa.toString());
		bpa.setFeetypecode(BPA_PERMITFEE_CODE);
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

	@PostMapping(value = "/savebpasiteinspection.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPASiteInspection(@RequestBody BpaSiteInspection bpa,
			@RequestParam(name = "processcode", required = false) Integer fromprocesscode,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		bpa.setImageFile(bpa.getReport());
		/*
		 * String base64ImageString = bpa.get("report").toString(); final Pattern mime =
		 * Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*"); final Matcher
		 * matcher = mime.matcher(base64ImageString); if (!matcher.find()) LOG.info("");
		 * else LOG.info(matcher.group(1).toLowerCase());
		 */
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPASiteInspection(bpa, USERCODE, fromprocesscode, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
