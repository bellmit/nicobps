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

	@GetMapping("/paysrappfee.htm")
	public String paysrappfee(Model model, HttpServletRequest req) {
		SSI.processPayment(Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 4, 1);
		return "redirect:home.htm";
	}

	@GetMapping("/paysrregfee.htm")
	public String paysrregfee(Model model, HttpServletRequest req) {
		SSI.processPayment(Integer.valueOf(req.getSession().getAttribute("usercode").toString()), 6, 2);
		return "redirect:home.htm";
	}

	@PostMapping("/listLicensees.htm")
	public @ResponseBody List<Map<String, Object>> listLicensees() {
		return SSI.listLicensees();
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

//	@PostMapping("/verifyStakeHolder.htm")
//	public @ResponseBody boolean verifyStakeHolder(Integer usercode) {
//		return SSI.updateStakeholder(usercode,4);		
//	}
//
//	@PostMapping("/approveStakeHolder.htm")
//	public @ResponseBody boolean approveStakeHolder(Integer usercode) {
//		return SSI.updateStakeholder(usercode,6);		
//	}

	@PostMapping("/updateStakeholder.htm")
	public @ResponseBody boolean updateStakeholder(Integer usercode, Integer toprocesscode, String remarks) {
		return SSI.updateStakeholder(usercode, toprocesscode, remarks);
	}

	@PostMapping("/listNextProcess.htm")
	public @ResponseBody List<Map<String, Object>> listNextProcess(@RequestBody Map<String, Object> params) {
		return serviceUtilInterface.getNextProcessflow(1, Integer.valueOf(params.get("currentprocesscode").toString()));
	}

}
