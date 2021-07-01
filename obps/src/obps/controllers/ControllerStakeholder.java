package obps.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.ServiceStakeholderInterface;
import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;

@Controller
public class ControllerStakeholder {
	@Autowired
	private ServiceStakeholderInterface SSI;
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@GetMapping("/srverify.htm")
	public String verification(Model model) {
		model.addAttribute("pageType", "Verification");
		return "stakeholder/srverify";
	}

	@GetMapping("/ulbregistration.htm")
	public String ulbregistration(Model model, String errorMsg) {
		model.addAttribute("registeringoffices", serviceUtilInterface.listRegisteringOffices());
		model.addAttribute("errorMsg", errorMsg);
		return "stakeholder/ulbregistration";
	}

	@PostMapping("/ulbregistration.htm")
	public String ulbRegistration(Model model,Integer officecode,HttpServletRequest req) {
		
		String applicationcode=SSI.ulbRegistration(officecode, Integer.valueOf(req.getSession().getAttribute("usercode").toString()));
//		String applicationcode="ALREADY_REPORTED";
		if(applicationcode.equals("ALREADY_REPORTED")||applicationcode.equals("false")) {
			model.addAttribute("errorMsg",applicationcode);
			return "redirect:ulbregistration.htm";
		}
		Map<String, Object> url=serviceUtilInterface.getCurrentProcessStatus(1, applicationcode).get(0);
		return "redirect:"+url.get("pageurl")+"?applicationcode="+applicationcode+"&officecode="+officecode;
	}

	@GetMapping("/skregistrationconfirmation.htm")
	public String paysuccess(Model model,Integer officecode,String applicationcode,HttpServletRequest req) {
		SSI.updateStakeholder(officecode, applicationcode, Integer.valueOf(req.getSession().getAttribute("usercode").toString()),"Verification Pending");
		model.addAttribute("applicationcode",applicationcode);
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

	@PostMapping("/listLicensees.htm")
	public @ResponseBody List<Map<String, Object>> listLicensees() {
		return SSI.listLicensees();
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
	public @ResponseBody boolean updateStakeholder(Integer officecode, String applicationcode, Integer usercode,
			Integer toprocesscode, String remarks,ModelMap model) {
		model.addAttribute("remarkserror","Size too big");
		return SSI.updateStakeholder(officecode, applicationcode, usercode, toprocesscode, remarks);
	}

	@PostMapping("/listNextProcess.htm")
	public @ResponseBody List<Map<String, Object>> listNextProcess(@RequestBody Map<String, Object> params) {
		return serviceUtilInterface.getAllNextProcessflows(1,
				Integer.valueOf(params.get("currentprocesscode").toString()));
	}

}
