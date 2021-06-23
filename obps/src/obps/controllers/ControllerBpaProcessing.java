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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.services.ServiceBPAInterface;
import obps.util.application.CommonMap;

@Controller
public class ControllerBpaProcessing {
	private static final Logger LOG = Logger.getLogger(ControllerBuildingPermit.class.toGenericString());
	private static final String PARENT_URL_MAPPING = "/bpa";
	private static final String REDIRECT_MAPPING = "redirect:";
	private static Integer USERCODE;
	private static String appcode;
	private static String pathurl;
	
	@Autowired
	private ServiceBPAInterface SBI;

	@GetMapping(value = "/bpaapproval.htm")
	public String bpaApproval(HttpServletRequest req, Model model, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: "+req.getServletPath());
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("bpainbox.htm");
			
			model.addAttribute("applicationcode", applicationcode);
			appcode = applicationcode;
			pathurl = req.getServletPath();
			return PARENT_URL_MAPPING.concat("/approval");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpaapprovalofsiteplan.htm")
	public String bpaApprovalSitePlan(HttpServletRequest req, Model model, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: "+req.getServletPath());
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("bpainbox.htm");
			
			model.addAttribute("applicationcode", applicationcode);
			appcode = applicationcode;
			pathurl = req.getServletPath();
			return PARENT_URL_MAPPING.concat("/approvalofsiteplan");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpainbox.htm")
	public String bpaInbox() {
		if(USERCODE != null)
			return PARENT_URL_MAPPING.concat("/inbox");
		else
			return "redirect:login.htm";
	}

	@GetMapping(value = "/bparejectedapplications.htm")
	public String bpaRejectedApplications(HttpServletRequest req) {
		LOG.info("URL: "+req.getServletPath());
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			return PARENT_URL_MAPPING.concat("/rejectedapplications");
		}
		return "redirect:login.htm";
	}

	@GetMapping(value = "/bpasiteinspection.htm")
	public String bpaSiteInspection(HttpServletRequest req, Model model, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: "+req.getServletPath());
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("bpainbox.htm");
			
			model.addAttribute("applicationcode", applicationcode);
			appcode = applicationcode;
			pathurl = req.getServletPath();
			return PARENT_URL_MAPPING.concat("/siteinspection");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/bpascrutinyofdocumentsandsiteplan.htm")
	public String bpaScrutinyOfDocumentsAndSitePlan(HttpServletRequest req, Model model, @RequestParam(required = false) String applicationcode) {
		LOG.info("URL: "+req.getServletPath());
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			USERCODE = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute("applicationcode", applicationcode);
			if(applicationcode == null || applicationcode.isEmpty())
				return REDIRECT_MAPPING.concat("bpainbox.htm");
			
			appcode = applicationcode;
			pathurl = req.getServletPath();
			return PARENT_URL_MAPPING.concat("/scrutinyofdocumentsandsiteplan");
		}
		return "redirect:login.htm";
	}
	
	@GetMapping(value = "/commonprocessingaction.htm")
	public String commonprocessingaction() {
		LOG.info("URL: commonprocessingaction.htm");
		if(SBI.checkAccessGrantStatus(USERCODE, appcode, pathurl))
			return PARENT_URL_MAPPING.concat("/commonprocessingaction");
		else
			return "";
	}
	
	@GetMapping(value = "/documentdetails.htm")
	public String documentDetails() {
		LOG.info("URL: documentdetails.htm");
		return PARENT_URL_MAPPING.concat("/documentdetails");
	}

	@GetMapping(value = "/fileviewmodal.htm")
	public String modal() {
		LOG.info("URL: fileviewmodal.htm");
		return PARENT_URL_MAPPING.concat("/fileviewmodal");
	}
	
	@GetMapping(value = "/processtrackstatus.htm")
	public String processTrackStatus() {
		LOG.info("URL: processtrackstatus.htm");
		return PARENT_URL_MAPPING.concat("/processtrackstatus");
	}

	@GetMapping(value = "/scrutinydetails.htm")
	public String scrutinyDetails() {
		LOG.info("URL: scrutinydetails.htm");
		return PARENT_URL_MAPPING.concat("/scrutinydetails");
	}
	
	@GetMapping(value = "/sitereportdetails.htm")
	public String siteReportDetails() {
		LOG.info("URL: sitereportdetails.htm");
		return PARENT_URL_MAPPING.concat("/sitereportdetails");
	}


	/* GET */
	@GetMapping(value = "/getBpaApplicationDetailsV2.htm")
	public @ResponseBody Map<String, Object> getBpaApplicationDetailsV2(@RequestParam(name = "param") String applicationcode) {
		return SBI.getBpaApplicationDetails(applicationcode);	
	};

	@GetMapping(value = "/getCurrentProcessTaskStatus.htm")
	public @ResponseBody Map<String, Object> getCurrentProcessTaskStatus(@RequestParam(name = "param") String applicationcode) {
		return SBI.getCurrentProcessTaskStatus(USERCODE, applicationcode);
	};
	
	@GetMapping(value = "/getEdcrDetailsV3.htm")
	public @ResponseBody Map<String, Object> getEdcrDetailsByApplicationcode(@RequestParam(name = "param") String applicationcode) {
		return SBI.getEdcrDetailsV2(applicationcode);
	};
	
	@GetMapping(value = "/listbpapplications.htm")
	public @ResponseBody List<Map<String, Object>> listBPApplications() {
		return SBI.listBPApplications(USERCODE);
	};

	@GetMapping(value = "/listNextProcessingUsers.htm")
	public @ResponseBody List<CommonMap> listNextProcessingUsers(@RequestParam(name = "param") String applicationcode) {
		return SBI.listNextProcessingUsers(USERCODE, applicationcode);
	};
	
	@GetMapping(value = "/listRejectedApplications.htm")
	public @ResponseBody List<Map<String, Object>> listRejectedApplications() {
		return SBI.listRejectedApplications(USERCODE);
	};
	
	@GetMapping(value = "/listSiteReportDetails.htm")
	public @ResponseBody List<Map<String, Object>> listSiteReportDetails(@RequestParam(name = "param") String applicationcode) {
		return SBI.listSiteReportDetails(USERCODE, applicationcode);
	};

	/* CREATE */
	@PostMapping(value = "/processbpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> processbpapplication(@RequestBody BpaProcessFlow data,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		data.setFromusercode(USERCODE);
		if (SBI.processBPApplication(data, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/rejectbpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> rejectBPApplication(@RequestBody BpaProcessFlow data,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (USERCODE == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
		
		data.setFromusercode(USERCODE);
		if (SBI.rejectBPApplication(data, response)) {
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
