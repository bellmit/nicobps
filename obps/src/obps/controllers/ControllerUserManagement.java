package obps.controllers;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.util.application.ServiceUtilInterface;
import obps.util.common.Utilty;
import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Pageurls;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.models.Userlogin;
import obps.services.ServiceUserManagementInterface;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerUserManagement {
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired
	private ServiceUserManagementInterface serviceUserManagementInterface;

	@Resource
	private Environment environment;

	public static HttpSession ssn() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	// =================================Registration====================================//
	@RequestMapping("/signup.htm")
	public String signup() {
		return "signup";
	}

	@GetMapping(value = "/initSignupForm.htm")
	public @ResponseBody Map<String, Object> initSignupForm(HttpServletRequest request) {
		String issms = environment.getProperty("app.prop.issms");
		String isemail = environment.getProperty("app.prop.isemail");

		System.out.println("issms : " + issms);
		System.out.println("isemail : " + isemail);

		Map<String, Object> data = new LinkedHashMap<>();
		data.put("listLicenseetypes", serviceUtilInterface.listLicenseetypes());
		data.put("listStates", serviceUtilInterface.listStates());
		data.put("listDistricts", serviceUtilInterface.listDistricts());
		data.put("listLicenseesregistrationsm", serviceUtilInterface.listLicenseesregistrationsm());
		data.put("issms", issms);
		data.put("isemail", isemail);
		return data;
	}

	@PostMapping("/submitSignupDetails.htm")
	// public ResponseEntity<?> submitProfilePersonalDetails(@RequestBody
	// Map<String,Object> param,HttpServletRequest request)
	public ResponseEntity<?> submitProfilePersonalDetails(@RequestParam Map<String, Object> param,
			HttpServletRequest request) {
		// for (Map.Entry<String, Object> entry : param.entrySet()){
		// System.out.println(entry.getKey() +" ::: " + entry.getValue());
		// }
		// ==========================================================================//
		String usersessioncaptcha = (String) request.getSession().getAttribute("CAPTCHA_KEY");
		String userresponsecaptcha = (String) param.get("userresponsecaptcha");
		String isotp = (String) param.get("isotp");
		String issms = environment.getProperty("app.prop.issms");
		String isemail = environment.getProperty("app.prop.isemail");

		System.out.println("issms : " + issms);
		System.out.println("isemail : " + isemail);
		System.out.println("isotp : " + isotp);
		System.out.println("-------------------------");

		if (usersessioncaptcha == null || userresponsecaptcha == null
				|| !usersessioncaptcha.trim().equals(userresponsecaptcha.trim())) {
			return ResponseEntity.badRequest().body(new String("Please check your entered captcha!"));
		}
		if (serviceUserManagementInterface.checkEmailExistance((String) param.get("username"))) {
			return ResponseEntity.badRequest().body(new String("Email already exists!"));
		}
		if (serviceUserManagementInterface.checkMobileExistance((String) param.get("mobileno"))) {
			return ResponseEntity.badRequest().body(new String("Mobile already exists!"));
		}
		String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
		param.put("afrcode", afrcode);
		if (issms.equals("Y") || isemail.equals("Y")) {
			if (isotp.equals("N")) {
				if (issms.equals("Y")) {
					Integer mobileotp = 123;// Utilty.getRandomNumber();
					request.getSession().setAttribute("mobileotp", mobileotp.toString());
					// Send SMS
				}
				if (isemail.equals("Y")) {
					Integer emailotp = 123;// Utilty.getRandomNumber();
					request.getSession().setAttribute("emailotp", emailotp.toString());
					// Send Email
				}
				return ResponseEntity.ok(new String("0"));
			} else {
				if (isemail.equals("Y")) {
					String emailotpR = (String) param.get("emailotp");
					String emailotpS = (String) request.getSession().getAttribute("emailotp");
					if (emailotpR == null || !emailotpR.equals(emailotpS)) {
						return ResponseEntity.badRequest().body(new String("Email verification otp doesn't match!"));
					}
				}
				if (issms.equals("Y")) {
					String mobileotpR = (String) param.get("mobileotp");
					String mobileotpS = (String) request.getSession().getAttribute("mobileotp");
					if (mobileotpR == null || !mobileotpR.equals(mobileotpS)) {
						return ResponseEntity.badRequest().body(new String("Mobile verification otp doesn't match!"));
					}
				}

				String usercode = serviceUserManagementInterface.getMaxUsercode() + "";
				param.put("usercode", usercode);
				if (serviceUserManagementInterface.createUser(param)) {
					request.getSession().setAttribute("usercode", usercode);
					// return ResponseEntity.ok(new String("Details submitted successfully!"));
					return ResponseEntity.ok(new String("1"));
				} else {
					return ResponseEntity.badRequest().body(new String("Unable to process request!"));
				}
			}
		} else {
			String usercode = serviceUserManagementInterface.getMaxUsercode() + "";
			param.put("usercode", usercode);
			if (serviceUserManagementInterface.createUser(param)) {
				request.getSession().setAttribute("usercode", usercode);
				// return ResponseEntity.ok(new String("Details submitted successfully!"));
				return ResponseEntity.ok(new String("1"));
			} else {
				return ResponseEntity.badRequest().body(new String("Unable to process request!"));
			}
		}

	}

	// =================================Upload
	// Enclosures====================================//
	@RequestMapping("/uploadenclosuresext.htm")
	public String uploadenclosuresext() {
		return "uploadenclosuresext";
	}

	@RequestMapping("/uploadenclosuresint.htm")
	public String uploadenclosuresint() {
		return "uploadenclosuresint";
	}

	@GetMapping(value = "/initUploadEnclosuresForm.htm")
	public @ResponseBody Map<String, Object> initUploadEnclosuresForm(HttpServletRequest request) {
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("listEnclosures", serviceUtilInterface.listEnclosures(Short.valueOf("1")));
		return data;
	}

	@PostMapping("/submitEnclosureDetails.htm")
	public ResponseEntity<?> submitEnclosureDetails(@RequestParam Map<String, Object> param,
			HttpServletRequest request) {
		String usersessioncaptcha = (String) request.getSession().getAttribute("CAPTCHA_KEY");
		String userresponsecaptcha = (String) param.get("userresponsecaptcha");

		if (usersessioncaptcha == null || userresponsecaptcha == null
				|| !usersessioncaptcha.trim().equals(userresponsecaptcha.trim())) {
			return ResponseEntity.badRequest().body(new String("Please check your entered captcha!"));
		}

		String usercode = (String) request.getSession().getAttribute("usercode");

		String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
		param.put("afrcode", afrcode);

		if (usercode != null) {
			param.put("usercode", usercode);
		} else {
			return ResponseEntity.badRequest().body(new String("Unable to process request!"));
		}

		if (serviceUserManagementInterface.submitEnclosureDetails(param)) {
			return ResponseEntity.ok(new String("The documents have been uploaded successfully."));
		} else {
			return ResponseEntity.badRequest().body(new String("Unable to process request!"));
		}
	}

	// =================================Change
	// Password====================================//
	@RequestMapping("/changepassword.htm")
	public String changepassword() {
		return "changepassword";
	}

	@PostMapping("/updatePassword.htm")
	public ResponseEntity<?> updatePassword(@RequestParam Map<String, Object> param, HttpServletRequest request) {
		String usersessioncaptcha = (String) request.getSession().getAttribute("CAPTCHA_KEY");
		String userresponsecaptcha = (String) param.get("userresponsecaptcha");

		if (usersessioncaptcha == null || userresponsecaptcha == null
				|| !usersessioncaptcha.trim().equals(userresponsecaptcha.trim())) {
			return ResponseEntity.badRequest().body(new String("Please check your entered captcha!"));
		}

		String usercode = (String) request.getSession().getAttribute("usercode");
		if (usercode != null) {
			param.put("usercode", usercode);
		} else {
			return ResponseEntity.badRequest().body(new String("Unable to process request!"));
		}

		if (serviceUserManagementInterface.updatePassword(param)) {
			return ResponseEntity.ok(new String("Password updated successfully!"));
		} else {
			return ResponseEntity.badRequest().body(new String("Unable to process request!"));
		}
	}

	@GetMapping("/createuser.htm")
	public String createuser() {
		return "initialization/createuser";
	}

	@PostMapping(value = "/createuser.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> createUser(@RequestBody Map<String, Object> user) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String usercode = serviceUserManagementInterface.getMaxUsercode() + "";
		user.put("usercode", usercode);
		user.put("usertype", "F");

		if (serviceUserManagementInterface.createUser(user)) {
			response.put("code", 200);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		response.put("code", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateuser.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateUser(@RequestBody Userlogin user) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (serviceUserManagementInterface.updateUser(user)) {
			response.put("code", 200);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		response.put("code", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listUsers.htm")
	public @ResponseBody List<Userlogin> listUsers() {

		return serviceUserManagementInterface.listUsers();
	}

	////////////////////////////////////////////////////////////////////////////
	@GetMapping("/createurl.htm")
	public String createurl() {
		return "initialization/createurl";
	}

	@PostMapping(value = "/saveurl.htm")
	public ResponseEntity<String> saveurl(@RequestBody Pageurls url) {

		return ResponseEntity.ok().body(serviceUserManagementInterface.savePageurl(url));
	}

	@GetMapping(value = "/listUrls")
	public @ResponseBody List<Pageurls> listUrls() {
		return serviceUserManagementInterface.listUrls();
	}

	///////////////////////////////////////////////////////////////////////////
	@GetMapping("/accesscontrol.htm")
	public String accesscontrol() {
		return "initialization/accesscontrol";
	}

	@GetMapping(value = "/listUserAndMappedPages.htm")
	public @ResponseBody List<Userlogin> listUserAndMappedPages() {

		return serviceUserManagementInterface.listUserAndMappedPages();
	}

	@PostMapping(value = "/saveUserpages.htm")
	public @ResponseBody String saveUserpages(@RequestBody List<Map<String, Object>> userpages) {

		return serviceUserManagementInterface.saveUserpages(userpages);
	}

	

}
