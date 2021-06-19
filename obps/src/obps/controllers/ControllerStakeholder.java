package obps.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String ulbregistration(Model model, HttpServletRequest req) {
		model.addAttribute("registeringoffices", serviceUtilInterface.listRegisteringOffices());
		return "stakeholder/ulbregistration";
	}

	@PostMapping("/ulbregistration.htm")
	public @ResponseBody String ulbRegistration(Integer officecode, HttpServletRequest req) {
		return SSI.ulbRegistration(officecode, Integer.valueOf(req.getSession().getAttribute("usercode").toString()));
	}

	@GetMapping("/paysuccess.htm")
	public String paysuccess(HttpServletRequest req, String applicationcode, Integer feecode, Integer feeamount) {
		return "stakeholder/payresponse";
	}

	@GetMapping("/paysrappfee.htm")
	public String paysrappfeepost(HttpServletRequest req, String applicationcode, Integer feecode, Integer feeamount,
			Integer officecode) {

//		SSI.processPayment(Integer.valueOf(req.getSession().getAttribute("usercode").toString()), applicationcode,
//				feecode, feeamount,
//				(Integer) (serviceUtilInterface.getNextProcessflow(1, applicationcode)).get("toprocesscode"));
		return "redirect:paymentconfirmation.htm?modulecode=1&applicationcode=" + applicationcode + "&usercode="
				+ Integer.valueOf(req.getSession().getAttribute("usercode").toString()) + "&feecode=" + feecode
				+ "&feeamount=" + feeamount + "&toprocesscode="
				+ (Integer) (serviceUtilInterface.getNextProcessflow(1, applicationcode)).get("toprocesscode");
	}

	@GetMapping("/paysrregfee.htm")
	public String paysrregfee(HttpServletRequest req, String applicationcode, Integer officecode) {
		Map<String, Object> fee = SSI.getFeeMaster(officecode,
				Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 2);
//		SSI.processPayment(Integer.valueOf(req.getSession().getAttribute("usercode").toString()), applicationcode,
//				Integer.valueOf(fee.get("feecode").toString()), Integer.valueOf(fee.get("feeamount").toString()),
//				(Integer) (serviceUtilInterface.getNextProcessflow(1, applicationcode)).get("toprocesscode"));
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
			Integer toprocesscode, String remarks) {
		return SSI.updateStakeholder(officecode, applicationcode, usercode, toprocesscode, remarks);
	}

	@PostMapping("/listNextProcess.htm")
	public @ResponseBody List<Map<String, Object>> listNextProcess(@RequestBody Map<String, Object> params) {
		return serviceUtilInterface.getAllNextProcessflows(1,
				Integer.valueOf(params.get("currentprocesscode").toString()));
	}

}
