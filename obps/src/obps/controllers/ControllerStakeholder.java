package obps.controllers;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.ServiceStakeholderInterface;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;
import obps.validators.ExtendValidityValidator;
import obps.validators.StakeHolderValidator;

@Controller
public class ControllerStakeholder {
	@Autowired
	private ServiceStakeholderInterface SSI;
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@Autowired
	private ExtendValidityValidator extendValidator;

	private List<String> applicationCalled = new LinkedList<String>();

	@Autowired
	private StakeHolderValidator stakeHolderValidator;

	@GetMapping("/samplepage.htm")
	public String samplepage(Model model) {
		return "z_samplepage";
	}

	@GetMapping("/srverify.htm")
	public String verification(Model model,@RequestParam(required = false) String processcode) {
		if(processcode!=null) {
			model.addAttribute("processcode",processcode);				
		}		
		model.addAttribute("pageType", "Verification");
		return "stakeholder/srverify";
	}

	@GetMapping("/ulbregistration.htm")
	public String ulbregistration(Model model, String errorMsg, HttpServletRequest req) {
		String usercode = (String) req.getSession().getAttribute("usercode");
		String licenseetypecode = (String) req.getSession().getAttribute("licenseetypecode");
		if (serviceUtilInterface.listEnclosuresNotUploades(Short.valueOf("1"), Integer.valueOf(usercode),
				Short.valueOf(licenseetypecode)).size() != 0) {
			model.addAttribute("errorMsg", "REQD_DOCUMENTS_INCOMPLETE");
			return "stakeholder/ulbregistration";
		}
		model.addAttribute("errorMsg", errorMsg);
		model.addAttribute("registeringoffices", serviceUtilInterface.listRegisteringOffices());
		return "stakeholder/ulbregistration";
	}

	@PostMapping("/ulbregistration.htm")
	public String ulbRegistration(Model model, Integer officecode, HttpServletRequest req) {
		String usercode = (String) req.getSession().getAttribute("usercode");
		Map<String, Object> errorList = SSI.getLicenceeValidity(Integer.valueOf(usercode.toString()), officecode);
		if (!errorList.isEmpty()) {
			model.addAttribute("registeringoffices", serviceUtilInterface.listRegisteringOffices());
			model.addAttribute("errorMapList", errorList);
			return "stakeholder/ulbregistration";
		}
		String applicationcode = SSI.ulbRegistration(officecode, Integer.valueOf(usercode));
		if (applicationcode.equals("ALREADY_REPORTED") || applicationcode.equals("false")) {
			model.addAttribute("errorMsg", applicationcode);
			model.addAttribute("registeringoffices", serviceUtilInterface.listRegisteringOffices());
			return "stakeholder/ulbregistration";
		}
		Map<String, Object> url = serviceUtilInterface.getCurrentProcessStatus(1, applicationcode).get(0);
		return "redirect:" + url.get("pageurl") + "?applicationcode=" + applicationcode + "&officecode=" + officecode;
	}

	@GetMapping("/skregistrationconfirmation.htm")
	public String paysuccess(Model model, Integer officecode, String applicationcode, HttpServletRequest req) {
		if (!applicationCalled.contains(applicationcode)) {
			SSI.updateStakeholder(officecode, applicationcode,
					Integer.valueOf(req.getSession().getAttribute("usercode").toString()), "Verification Complete");
			applicationCalled.add(applicationcode);
		}
		model.addAttribute("applicationcode", applicationcode);
		return "stakeholder/skregistrationconfirmation";
	}

	@GetMapping("/paysrappfee.htm")
	public String paysrappfeepost(HttpServletRequest req, String applicationcode, Integer feecode, Integer feeamount,
			Integer officecode) {
		Map<String, Object> fee = SSI.getFeeMaster(officecode,
				Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 1);
		return "redirect:paymentconfirmation.htm?modulecode=1&applicationcode=" + applicationcode + "&usercode="
				+ Integer.valueOf(req.getSession().getAttribute("usercode").toString()) + "&feecode="
				+ Integer.valueOf(fee.get("feecode").toString()) + "&feeamount="
				+ Integer.valueOf(fee.get("feeamount").toString()) + "&toprocesscode="
				+ (Integer) (serviceUtilInterface.getNextProcessflow(1, applicationcode)).get("toprocesscode");
	}

	@GetMapping("/paysrregfee.htm")
	public String paysrregfee(HttpServletRequest req, String applicationcode, Integer officecode) {
		Map<String, Object> fee = SSI.getFeeMaster(officecode,
				Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 2);
		return "redirect:paymentconfirmation.htm?modulecode=1&applicationcode=" + applicationcode + "&usercode="
				+ Integer.valueOf(req.getSession().getAttribute("usercode").toString()) + "&feecode="
				+ Integer.valueOf(fee.get("feecode").toString()) + "&feeamount="
				+ Integer.valueOf(fee.get("feeamount").toString()) + "&toprocesscode="
				+ (Integer) (serviceUtilInterface.getNextProcessflow(1, applicationcode)).get("toprocesscode");
	}

	@PostMapping("/getLicenseValidity.htm")
	public @ResponseBody Map<String, Object> getLicenseValidity(HttpServletRequest req, Integer officecode) {
		return SSI.getLicenceeValidity(Integer.valueOf(req.getSession().getAttribute("usercode").toString()),
				officecode);
	}

	@PostMapping("/listLicensees.htm")
	public @ResponseBody List<Map<String, Object>> listLicensees(HttpServletRequest req,@RequestParam(required = false) String processcode) {		
		List<CommonMap> offices = serviceUtilInterface.listUserOffices();
		Integer officecode = Integer.valueOf((!offices.isEmpty()) ? offices.get(0).getKey() : "0");
		if(processcode!=null && processcode.trim().length()>0) {
			return SSI.listLicensees(Integer.valueOf(req.getSession().getAttribute("usercode").toString()),officecode != null ? officecode : 0,Integer.valueOf(processcode));
		}else {
			return SSI.listLicensees(Integer.valueOf(req.getSession().getAttribute("usercode").toString()),officecode != null ? officecode : 0);	
		}
		
	}

	@PostMapping("/getLicensee.htm")
	public @ResponseBody List<Map<String, Object>> getLicensee(Integer usercode) {
		return serviceUtilInterface.getLicensee(usercode);
	}

	@PostMapping("/listEnclosures.htm")
	public @ResponseBody List<CommonMap> listEnclosures() {
		return serviceUtilInterface.listEnclosures(Short.valueOf("1"));
	}

	@PostMapping("/getEnclosure.htm")
	public @ResponseBody String getEnclosures(Integer usercode, Integer enclosurecode) {
		byte[] file = SSI.getEnclosure(usercode, enclosurecode);
		return (file != null) ? Base64.getEncoder().encodeToString(file) : null;
	}

	@PostMapping("/getApplicationfee.htm")
	public @ResponseBody Map<String, Object> verifyStakeHolder(HttpServletRequest req, Integer officecode) {

		return SSI.getFeeMaster(officecode, Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 1);
	}

	@PostMapping("/updateStakeholder.htm")
	public @ResponseBody String updateStakeholder(Integer officecode, String applicationcode, Integer usercode,
			Integer toprocesscode, String remarks, ModelMap model) {
		String res = "";

		if (officecode != null && applicationcode != null && usercode != null && toprocesscode != null
				&& remarks != null) {
			res = stakeHolderValidator.validateStackHolder(officecode, applicationcode, usercode, toprocesscode,
					remarks);

			if (res != "")
				return res;
			else {
				if (SSI.updateStakeholder(officecode, applicationcode, usercode, toprocesscode, remarks))
					res = "success";
				else
					res = "false";
			}
		}

		return res;
	}

	@PostMapping("/listNextProcess.htm")
	public @ResponseBody List<Map<String, Object>> listNextProcess(@RequestBody Map<String, Object> params) {
		return serviceUtilInterface.getAllNextProcessflows(1,
				Integer.valueOf(params.get("currentprocesscode").toString()));
	}

	@GetMapping("/extendstakeholdervalidity.htm")
	public String extendstakeholdervalidity(Model model, HttpServletRequest req,
			@RequestParam Map<String, String> params) {

		model.addAttribute("offices", serviceUtilInterface.listUserOffices());

		return "stakeholder/extendstakeholdervalidity";
	}

	@PostMapping(value = "/listStakeholders.htm")
	public @ResponseBody Map<String, Object> listStakeholders(HttpServletRequest req,
			@RequestParam Map<String, String> params) {
		Integer usercode = Integer.valueOf(req.getSession().getAttribute("usercode").toString());

		Map<String, Object> data = new LinkedHashMap<>();
		if (usercode == 1) {
			data.put("listStakeholders",
					serviceUtilInterface.listStakeholdersMain(Integer.parseInt(params.get("officecode"))));
		} else {
			data.put("listStakeholders",
					serviceUtilInterface.listStakeholders(Integer.parseInt(params.get("officecode"))));
		}

		return data;
	}

	//

	@PostMapping("/getValidity.htm")
	public @ResponseBody List<Map<String, Object>> getValidity(HttpServletRequest req,
			@RequestParam Map<String, String> params) {

		return SSI.getValidity(Integer.parseInt(params.get("usercode")));
	}

	@PostMapping("/extendValidity.htm")
	public ResponseEntity<?> extendValidity(HttpServletRequest req, @RequestParam Map<String, String> params) {
		String res = "false";
		Integer extendedby = Integer.valueOf(req.getSession().getAttribute("usercode").toString());
		if (extendValidator.validateExtendValidity(params, extendedby)) {
			return ResponseEntity.badRequest().body(new String("Check input details"));
		}

		if (SSI.extendValidity(Short.parseShort(params.get("officecode")), Integer.parseInt(params.get("usercode")),
				params.get("extendedto"), extendedby)) {
			return ResponseEntity.ok(new String("1"));
		} else {
			return ResponseEntity.badRequest().body(new String(
					"Sorry, but we are unable to process the request at the moment. Please try again later."));
		}

	}
}
