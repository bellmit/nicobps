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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import obps.models.BpaApproval;
import obps.models.BpaProcessFlow;
import obps.models.BpaSiteInspection;
import obps.services.ServiceBPAInterface;
import obps.util.application.BPAConstants;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;
import obps.util.notifications.ServiceNotification;
import obps.validators.BpaValidator;

@Controller
@SessionAttributes({ "SESSION_PATHURL", "SESSION_USERCODE" })
public class ControllerBpaProcessing {
	private static final Logger LOG = Logger.getLogger(ControllerBpaProcessing.class.toGenericString());

	private static String pathurl;

	@Autowired
	private ServiceNotification serviceNotification;

	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@Autowired
	private ServiceBPAInterface SBI;
	@Autowired
	private BpaValidator Bvalid;

	@ModelAttribute("SESSION_USERCODE")
	Integer getSessionUsercode() {
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			return Integer.valueOf(session.getAttribute("usercode").toString());
		}
		return null;
	}

	Integer getSessionUsercode(ModelMap model) {
		model.remove(BPAConstants.SESSION_USERCODE);
		HttpSession session = ControllerLogin.session();
		if (session != null && session.getAttribute("user") != null && session.getAttribute("usercode") != null) {
			Integer usercode = Integer.valueOf(session.getAttribute("usercode").toString());
			model.addAttribute(BPAConstants.SESSION_USERCODE, usercode);
			return usercode;
		}
		return null;
	}

	@GetMapping(value = "/bpaprocess.htm")
	public String bpaProcess(HttpServletRequest req, @ModelAttribute("SESSION_USERCODE") Integer usercode, Model model,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("bpaprocess");
		if (usercode != null) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);
			String pageurl = BPAConstants.getProcessPage(model.getAttribute(BPAConstants.SESSION_PATHURL).toString());
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);

		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpaadministrativeapproval.htm")
	public String bpaAdministrativeApproval(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);
			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/administrativeapproval"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpaapprovalofsiteplan.htm")
	public String bpaApprovalSitePlan(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);

			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/approvalofsiteplan"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");

	}

	@GetMapping(value = "/bpacheckingofbpp.htm")
	public String bpaCheckingOfBpp(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);

			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/checkingofbpp"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpainbox.htm")
	public String bpaInbox(ModelMap model, @ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String processcode) {
		usercode = getSessionUsercode(model);
		if (usercode != null) {
			if (processcode != null) {
				model.addAttribute("processcode", processcode);
			}else {
				model.addAttribute("processcode", null);
			}
			return BPAConstants.PARENT_URL_MAPPING.concat("/inbox");
			
//			model.addAttribute("page","bpainbox");
//
//			return BPAConstants.PARENT_URL_MAPPING.concat("/bpacommon");
		}

		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bparejectedapplications.htm")
	public String bpaRejectedApplications(ModelMap model, @ModelAttribute("SESSION_USERCODE") Integer usercode) {
		usercode = getSessionUsercode(model);
		if (usercode != null) {
			return BPAConstants.PARENT_URL_MAPPING.concat("/rejectedapplications");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpascrutinyofbpp.htm")
	public String bpaScrutinyOfBpp(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);

			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/scrutinyofbpp"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpascrutinyofdocumentsandsiteplan.htm")
	public String bpaScrutinyOfDocumentsAndSitePlan(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);

			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/*
			 * return
			 * BPAConstants.PARENT_URL_MAPPING.concat("/scrutinyofdocumentsandsiteplan");
			 */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpasearch.htm")
	public String bpaSearch(ModelMap model, @ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: bpasearch.htm");
		usercode = getSessionUsercode(model);
		if (usercode != null && usercode > -1) {
			model.addAttribute("applicationcode", applicationcode);
			
//			model.addAttribute("page","bpasearch");
			
//			return BPAConstants.PARENT_URL_MAPPING.concat("/bpacommon");
			return BPAConstants.PARENT_URL_MAPPING.concat("/search");
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpasiteinspection.htm")
	public String bpaSiteInspection(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);
			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/siteinspection"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpastructuralcheck.htm")
	public String bpaStructuralCheck(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);
			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/structuralcheck"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	@GetMapping(value = "/bpatechnicalapproval.htm")
	public String bpaTechnicalApproval(HttpServletRequest req, Model model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(required = false) String applicationcode) {
		LOG.info("URL: " + req.getServletPath());
		if (usercode != null && usercode > -1) {
			if (applicationcode == null || applicationcode.isEmpty())
				return BPAConstants.REDIRECT_MAPPING.concat("bpainbox.htm");

			model.addAttribute("applicationcode", applicationcode);
			pathurl = req.getServletPath();
			model.addAttribute(BPAConstants.SESSION_PATHURL, pathurl);
			String pageurl = BPAConstants.getProcessPage(pathurl);
			return BPAConstants.PARENT_URL_MAPPING.concat(pageurl);
			/* return BPAConstants.REDIRECT_MAPPING.concat("bpaprocess.htm"); */
			/* return BPAConstants.PARENT_URL_MAPPING.concat("/technicalapproval"); */
		}
		return BPAConstants.REDIRECT_MAPPING.concat("login.htm");
	}

	/* COMPONENTS */
	@GetMapping(value = "/commonprocessingaction.htm")
	public String commonProcessingAction(ModelMap model, @ModelAttribute("SESSION_PATHURL") String pathurl,
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam String applicationcode) {
		LOG.info("URL: commonprocessingaction.htm");
		usercode = getSessionUsercode(model);
		/* if (SBI.checkActionAccessGrantStatus(USERCODE, appcode)) */
		if (SBI.checkPageAccessGrantStatus(usercode, applicationcode, pathurl))
			return BPAConstants.COMPONENT_URL_MAPPING.concat("/commonprocessingaction");
		else
			return "";
	}

	@GetMapping(value = "/commonprocessingdetails.htm")
	public String commonProcessingDetails() {
		LOG.info("URL: commonprocessingdetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/commonprocessingdetails");
	}

	@GetMapping(value = "/commonprocessingdocumentdetails.htm")
	public String commonProcessingDocumentDetails() {
		LOG.info("URL: commonprocessingdocumentdetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/processingdocumentdetails");
	}

	@GetMapping(value = "/documentdetails.htm")
	public String documentDetails() {
		LOG.info("URL: documentdetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/documentdetails");
	}

	@GetMapping(value = "/fileviewmodal.htm")
	public String modal() {
		LOG.info("URL: fileviewmodal.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/fileviewmodal");
	}

	@GetMapping(value = "/ownerdetails.htm")
	public String ownerDetails() {
		LOG.info("URL: ownerdetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/ownerdetails");
	}

	@GetMapping(value = "/processtrackstatus.htm")
	public String processTrackStatus() {
		LOG.info("URL: processtrackstatus.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/processtrackstatus");
	}

	@GetMapping(value = "/scrutinydetails.htm")
	public String scrutinyDetails() {
		LOG.info("URL: scrutinydetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/scrutinydetails");
	}

	@GetMapping(value = "/siteinspectiondetails.htm")
	public String siteReportDetails() {
		LOG.info("URL: siteinspectiondetails.htm");
		return BPAConstants.COMPONENT_URL_MAPPING.concat("/siteinspectiondetails");
	}

	/* GET */
	@GetMapping(value = "/getBpaApplicationDetailsV2.htm")
	public @ResponseBody Map<String, Object> getBpaApplicationDetailsV2(
			@RequestParam(name = "param") String applicationcode) {
		return SBI.getBpaApplicationDetails(applicationcode);
	};

	@GetMapping(value = "/getCurrentProcessTaskStatus.htm")
	public @ResponseBody Map<String, Object> getCurrentProcessTaskStatus(
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(name = "param") String applicationcode) {
		return SBI.getCurrentProcessTaskStatus(usercode, applicationcode);
	};

	@GetMapping(value = "/getEdcrDetailsV3.htm")
	public @ResponseBody Map<String, Object> getEdcrDetailsByApplicationcode(
			@RequestParam(name = "param") String applicationcode) {
		return SBI.getEdcrDetailsV2(applicationcode);
	};

	@GetMapping(value = "/listNextProcessingUsers.htm")
	public @ResponseBody List<CommonMap> listNextProcessingUsers(@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(name = "param") String applicationcode) {
		return SBI.listNextProcessingUsers(usercode, applicationcode);
	};

	@GetMapping(value = "/listNextProcessingUsersByProcesscode.htm")
	public @ResponseBody List<CommonMap> listNextProcessingUsersByProcesscode(
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(name = "param", required = false) String applicationcode,
			@RequestParam(name = "param1", required = false) Integer processcode) {
		return SBI.listNextProcessingUsers(applicationcode, processcode);
	};

	@GetMapping(value = "/listbpapplications.htm")
	public @ResponseBody List<Map<String, Object>> listBPApplications(ModelMap model,
			@RequestParam(value = "param", required = false) String processcode,
			@ModelAttribute("SESSION_USERCODE") Integer usercode) {
		usercode = getSessionUsercode(model);
		if (processcode != null && processcode.trim().length() > 0) {
			return SBI.listBPApplications(usercode, Integer.valueOf(processcode));
		} else {
			return SBI.listBPApplications(usercode);
		}
	};

	@GetMapping(value = "/listBPApplicationsStatus.htm")
	public @ResponseBody List<Map<String, Object>> listBPApplicationsStatus(ModelMap model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestParam(name = "param") String param) {
		usercode = getSessionUsercode(model);
		return SBI.listBPApplicationsStatus(usercode, param);
	};

	@GetMapping(value = "/listbpaconditions.htm")
	public @ResponseBody List<Map<String, Object>> listBPAConditions(
			@RequestParam(name = "param") String applicationcode) {
		return SBI.listBPAConditions(applicationcode);
	};

	@GetMapping(value = "/listBpaEnclosures.htm")
	public @ResponseBody List<Map<String, Object>> listBPAEnclosures(
			@RequestParam(name = "param") String applicationcode) {
		return SBI.listBPAEnclosures(applicationcode);
	};

	@GetMapping(value = "/listRejectedApplications.htm")
	public @ResponseBody List<Map<String, Object>> listRejectedApplications(ModelMap model,
			@ModelAttribute("SESSION_USERCODE") Integer usercode) {
		usercode = getSessionUsercode(model);
		return SBI.listRejectedApplications(usercode);
	};

	@GetMapping(value = "/listSiteInspectionQuestionnaires.htm")
	public @ResponseBody List<Map<String, Object>> listSiteInspectionQuestionnaires(
			@RequestParam(name = "param") String applicationcode) {
		return SBI.listSiteInspectionQuestionnaires(applicationcode);
	};

	/* CREATE */
	@PostMapping(value = "/approvebpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> approveApplication(
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaApproval bpa,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

//		System.out.println("usercodeeeeeeeeeeee  " + usercode);
		bpa.getProcessflow().setFromusercode(usercode);
//		System.out.println("usercodeeeeeeeeeeee  " + bpa.getProcessflow().getFromusercode());

// -------------------------------------------VALIDATION STARTS---------------------------------------------------------------------
		Bvalid.ValidateapproveApplication(bpa, response);
		if (!response.isEmpty()) {
			LOG.info("ErrorMap: " + response.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
// -------------------------------------------VALIDATION ENDS---------------------------------------------------------------------

		if (SBI.approveBPApplication(bpa, response)) {
			Map<String, Object> appdet = serviceUtilInterface
					.getApplicationDetails(bpa.getProcessflow().getApplicationcode());
			String applicationcode = bpa.getProcessflow().getApplicationcode();

			if (appdet != null && applicationcode != null) {
				if (!appdet.isEmpty() && !applicationcode.isEmpty()) {

					try {
						String emailid = appdet.get("username").toString();
						String mobileno = appdet.get("mobileno").toString();

						serviceNotification.sentNotification(2, "BPA_APPROVED", mobileno, emailid,
								new String[] { applicationcode }, new String[] { applicationcode });

					} catch (Exception e) {
						LOG.info("Exception while sending Notification : " + e);
					}
				} else {
					LOG.info(" Empty Inputs!!!");
				}
			} else {
				LOG.info(" Unable to send Notification!!!");
			}

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/processbpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> processbpapplication(
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaProcessFlow data,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		data.setFromusercode(usercode);
// -------------------------------------------VALIDATION STARTS---------------------------------------------------------------------		
		Bvalid.Validateprocessbpapplication(data, response);
		if (!response.isEmpty()) {
			LOG.info("ErrorMap: " + response.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
// -------------------------------------------VALIDATION ENDS---------------------------------------------------------------------
		if (SBI.processBPApplication(data, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/rejectbpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> rejectBPApplication(
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaProcessFlow data,
			BindingResult bindingResult) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		data.setFromusercode(usercode);
// -------------------------------------------VALIDATION STARTS---------------------------------------------------------------------
		Bvalid.ValidaterejectBPApplication(data, response);
		if (!response.isEmpty()) {
			LOG.info("response: " + response.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
// -------------------------------------------VALIDATION ENDS---------------------------------------------------------------------

		if (SBI.rejectBPApplication(data, response)) {
			Map<String, Object> appdet = serviceUtilInterface.getApplicationDetails(data.getApplicationcode());
			String applicationcode = data.getApplicationcode();

			if (appdet != null && applicationcode != null) {
				if (!appdet.isEmpty() && !applicationcode.isEmpty()) {

					try {
						String emailid = appdet.get("username").toString();
						String mobileno = appdet.get("mobileno").toString();

						serviceNotification.sentNotification(2, "BPA_REJECT", mobileno, emailid,
								new String[] { applicationcode }, new String[] { applicationcode });
					} catch (Exception e) {
						LOG.info("Exception while sending Notification : " + e);
					}
				} else {
					LOG.info(" Empty Inputs!!!");
				}
			} else {
				LOG.info(" Unable to send Notification!!!");
			}

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/savebpasiteinspection.htm")
	public ResponseEntity<HashMap<String, Object>> saveBPASiteInspection(@RequestBody BpaSiteInspection bpa,
			@ModelAttribute("SESSION_USERCODE") Integer usercode,
			@RequestParam(name = "processcode", required = false) Integer fromprocesscode, Model model,
			BindingResult bindingResult) {
		LOG.info("bpa: " + bpa);
		HashMap<String, Object> response = new HashMap<String, Object>();
		/*
		 * String base64ImageString = bpa.get("report").toString(); final Pattern mime =
		 * Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*"); final Matcher
		 * matcher = mime.matcher(base64ImageString); if (!matcher.find()) LOG.info("");
		 * else LOG.info(matcher.group(1).toLowerCase());
		 */
// -------------------------------------------VALIDATION STARTS---------------------------------------------------------------------
		Bvalid.ValidatesaveBPASiteInspection(bpa, response);
		if (!response.isEmpty()) {
			LOG.info("ErrorMap: " + response.toString());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
// -------------------------------------------VALIDATION ENDS---------------------------------------------------------------------

		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		if (SBI.saveBPASiteInspection(bpa, usercode, fromprocesscode, response)) {
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(value = "/sendtocitizenbpapplication.htm")
	public ResponseEntity<HashMap<String, Object>> sendToCitizenBPApplication(
			@ModelAttribute("SESSION_USERCODE") Integer usercode, @RequestBody BpaProcessFlow data) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		if (usercode == null)
			return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);

		data.setFromusercode(usercode);
		if (SBI.sendToCitizenBPApplication(data, response)) {
//			Map<String, Object> appdet = serviceUtilInterface.getApplicationDetails(data.getApplicationcode());
//			String applicationcode = data.getApplicationcode();
//
//			if (appdet != null && applicationcode != null) {
//				if (!appdet.isEmpty() && !applicationcode.isEmpty()) {
//			try {
//				String emailid = appdet.get("username").toString();
//				String mobileno = appdet.get("mobileno").toString();
//
//				serviceNotification.sentNotification(2, "BPA_SENT_TO_CITIZEN", mobileno, emailid,
//						new String[] { applicationcode }, new String[] { applicationcode });
//			} catch (Exception e) {
//				LOG.info("Exception while sending Notification : " + e);
//			}

//				} else {
//					LOG.info(" Empty Inputs!!!");
//				}
//			} else {
//				LOG.info(" Unable to send Notification!!!");
//			}

			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} else
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/bpacommon.htm")
	public String getBPApplications(ModelMap model, @RequestParam(value = "param", required = false) String processcode,
			@ModelAttribute("SESSION_USERCODE") Integer usercode) {
		System.out.println("bpacommon");
		usercode = getSessionUsercode(model);
		if (usercode != null) {
			if (processcode != null && processcode.trim().length() > 0) {
				model.addAttribute("processcode", processcode);
			}else {
				model.addAttribute("processcode", null);
			}
			
		}
		model.addAttribute("page","bpainbox");

		return BPAConstants.PARENT_URL_MAPPING.concat("/bpacommon");
	};

}
