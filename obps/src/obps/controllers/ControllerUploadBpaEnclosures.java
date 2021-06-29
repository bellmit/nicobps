package obps.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.ServiceUploadBpaEnclosuersInterface;
import obps.util.application.ServiceUtilInterface;
import obps.validators.UploadEnclosuresValidatorInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerUploadBpaEnclosures {
	private static final String PARENT_URL_MAPPING = "/bpa";
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired
	private ServiceUploadBpaEnclosuersInterface ServiceUploadBpaEnclosuersInterface;
	
	@Autowired
	private UploadEnclosuresValidatorInterface uploadBpaEnclosuersValidatorInterface;

	@RequestMapping("/bpauploadenc.htm")
	public String uploadbpaenclosuresext(Model model, @RequestParam String applicationcode) {
		model.addAttribute("applicationcode", applicationcode);
		return PARENT_URL_MAPPING.concat("/uploadbpaenclosuresext");
	}

	@GetMapping(value = "/initUploadBpaEnclosuresForm.htm")
	public @ResponseBody Map<String, Object> initUploadBpaEnclosuresForm(HttpServletRequest request) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("listBpaEnclosures", serviceUtilInterface.listEnclosures(Short.valueOf("2")));
		return data;
	}

	@PostMapping("/submitBpaEnclosureDetails.htm")
	public ResponseEntity<?> submitBpaEnclosureDetails(@RequestParam Map<String, Object> param,
			HttpServletRequest request) {
		String usersessioncaptcha = (String) request.getSession().getAttribute("CAPTCHA_KEY");
		String userresponsecaptcha = (String) param.get("userresponsecaptcha");
		if (usersessioncaptcha == null || userresponsecaptcha == null
				|| !usersessioncaptcha.trim().equals(userresponsecaptcha.trim())) {
			return ResponseEntity.badRequest().body(new String("Please check your entered captcha!"));
		}
		String usercode = (String) request.getSession().getAttribute("usercode");
//		String usercode = "10";
		String appenclosurecode = ServiceUploadBpaEnclosuersInterface.getMaxAppEnclosureCode() + "";
		param.put("appenclosurecode", appenclosurecode);
		if (usercode != null) {
			param.put("usercode", usercode);
		} else {
			return ResponseEntity.badRequest().body(new String("Unable to process request!"));
		}

		
			if (ServiceUploadBpaEnclosuersInterface.submitBpaEnclosureDetails(param)) {
				System.out.println("in controller inside if  submitBpaEnclosureDetails(param))");
				return ResponseEntity.ok(new String("Documents uploaded successfully!"));

			} else {
				return ResponseEntity.badRequest().body(new String("Unable to process request!"));
			}
		
		
		
	}

}