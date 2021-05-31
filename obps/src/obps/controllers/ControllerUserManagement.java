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
			return ResponseEntity.ok(new String("Documents uploaded successfully!"));
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
			response.put("response", HttpStatus.CREATED);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateuser.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateUser(@RequestBody Userlogin user) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		if (serviceUserManagementInterface.updateUser(user)) {
			response.put("response", HttpStatus.CREATED);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		response.put("response", HttpStatus.OK);
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

	@GetMapping("/initlicenseesregistrationsm.htm")
	public String initlicenseesregistrationsm() {
		return "initialization/initlicenseesregistrationsm";
	}

	@PostMapping(value = "/initlicenseesregistrationsm.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> createlicenseesregistrationsm(
			@RequestBody Map<String, Object> licensee) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String licenseeregistrationcode = serviceUserManagementInterface.getMaxLicenseecode() + "";
		licensee.put("licenseeregistrationcode", licenseeregistrationcode);
//		check existance
		String sql = "";
		Object[] values = { licensee.get("licenseedescription") };
		sql = "SELECT * FROM masters.licenseesregistrationsm WHERE  LOWER(licenseedescription)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		if(!exist) {
		if (serviceUserManagementInterface.createLicenseeRegistration(licensee)) {
			response.put("response", HttpStatus.CREATED);
			response.put("data", 1);
			return ResponseEntity.ok().body(response);
		}
		}
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listLicensees.htm")
	public @ResponseBody List<LicenseesRegistrationsm> listLicenseesRegistrationsms() {

		return serviceUserManagementInterface.listLicenseesRegistrationsms();
	}

	@GetMapping("/listFeeTypes.htm")
	public @ResponseBody List<FeeTypes> listFeeTypes() {

		return serviceUserManagementInterface.listFeeTypes();
	}

	@PostMapping(value = "/updatelicenseesregistrationsm.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatelicenseesregistrationsm(
			@RequestBody LicenseesRegistrationsm licensee) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		// check existance
				String sql = "";
				Object[] values = { licensee.getLicenseedescription() };
				sql = "SELECT * FROM masters.licenseesregistrationsm WHERE  LOWER(licenseedescription)=LOWER(?) ";

				boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
				if(!exist) {
					if (serviceUserManagementInterface.updateLicenseesRegistrationsm(licensee)) {
						response.put("response", HttpStatus.CREATED);
						response.put("data", 1);
						return ResponseEntity.ok().body(response);
					}
				}
	
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/initfeetypes.htm")
	public String initfeetypes() {
		return "initialization/initfeetypes";
	}

	@PostMapping(value = "/initfeetypes.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initfeetypes(@RequestBody Map<String, Object> feetype) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String feetypecode = serviceUserManagementInterface.getMaxFeeTypecode() + "";
		feetype.put("feetypecode", feetypecode);
//		user.put("usertype", "F");
		// check existance
		String sql = "";
		Object[] values = { feetype.get("feetypedescription") };
		sql = "SELECT * FROM masters.feetypes WHERE  LOWER(feetypedescription)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		if(!exist) {
			if (serviceUserManagementInterface.initfeetypes(feetype)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}
		
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateinitfeetypes.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateinitfeetypes(@RequestBody FeeTypes feetype) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		// check existance
				String sql = "";
				Object[] values = { feetype.getFeetypedescription() };
				sql = "SELECT * FROM masters.feetypes WHERE  LOWER(feetypedescription)=LOWER(?) ";

				boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
				if(!exist) {
					if (serviceUserManagementInterface.updatefeetypes(feetype)) {
						response.put("response", HttpStatus.CREATED);
						response.put("data", 1);
						return ResponseEntity.ok().body(response);
					}
				}
		
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updatefeemaster.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatefeemaster(@RequestBody FeeMaster feemaster) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("feemaster:" + feemaster);
		// check existance
		String sql = "";
		Object[] values = { feemaster.getLicenseetypecode(), feemaster.getOfficecode(),
				feemaster.getFeetypecode() };
		sql = "SELECT * FROM masters.feemaster WHERE  licenseetypecode=? AND officecode=? AND feetypecode=? ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		if(!exist) {
			if (serviceUserManagementInterface.updatefeemaster(feemaster)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}
		
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updatesuboccupancy.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updatesuboccupancy(@RequestBody SubOccupancies suboccupancies) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("feemaster:" + suboccupancies);

		// check existance
		String sql = "";
		Object[] values = { suboccupancies.getSuboccupancycode(), suboccupancies.getDescription(),
				suboccupancies.getSuboccupancyname() };
		sql = "SELECT * FROM masters.suboccupancies WHERE  LOWER(suboccupancycode)=LOWER(?) AND LOWER(description)=LOWER(?) AND LOWER(suboccupancyname)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		if (!exist) {
			if (serviceUserManagementInterface.updatesuboccupancy(suboccupancies)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}

		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateusages.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateusages(@RequestBody Usages usages) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("update usages:::::" + usages);

		// check existance
		String sql = "";
		Object[] values = { usages.getUsagecode(), usages.getDescription(), usages.getUsagename() };
		sql = "SELECT * FROM masters.usages WHERE  LOWER(usagecode)=LOWER(?) AND LOWER(description)=LOWER(?) OR LOWER(usagename)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		if (!exist) {
			if (serviceUserManagementInterface.updateusages(usages)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}

		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/initoccupancies.htm")
	public String initoccupancies() {
		return "initialization/initoccupancies";
	}

	@GetMapping("/initsuboccupancies.htm")
	public String initsuboccupancies(Model model, HttpServletRequest req) {
		model.addAttribute("occupancies", serviceUtilInterface.listOccupancies());
		return "initialization/initsuboccupancies";
	}

	@GetMapping("/initusages.htm")
	public String initusages(Model model, HttpServletRequest req) {
		model.addAttribute("suboccupancies", serviceUtilInterface.listSubOccupancies());
		return "initialization/initusages";
	}

	@GetMapping("/initfeemaster.htm")
	public String initfeemaster(Model model, HttpServletRequest req) {
		model.addAttribute("offices", serviceUtilInterface.listOffices());
		model.addAttribute("licenseetypes", serviceUtilInterface.listLicenseetypes());
		model.addAttribute("feetypes", serviceUtilInterface.listFeetypes());

		return "initialization/initfeemaster";
	}

	@PostMapping(value = "/initfeemaster.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initfeemaster(@RequestBody Map<String, Object> feemaster) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		String feecode = serviceUserManagementInterface.getMaxFeeCode() + "";
		feemaster.put("feecode", feecode);
		// check existance
				String sql = "";
				Object[] values = { Integer.parseInt(feemaster.get("licenseetypecode").toString()) , Integer.parseInt(feemaster.get("officecode").toString()),
						Integer.parseInt(feemaster.get("feetypecode").toString()) };
				sql = "SELECT * FROM masters.feemaster WHERE  licenseetypecode=? AND officecode=? AND feetypecode=? ";

				boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
				if(!exist) {
					if (serviceUserManagementInterface.initfeemaster(feemaster)) {
						response.put("response", HttpStatus.CREATED);
						response.put("data", 1);
						return ResponseEntity.ok().body(response);
					}
				}
		
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/initsuboccupancies.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initsuboccupancies(@RequestBody Map<String, Object> suboccupancies) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		// check existance
		String sql = "";
		Object[] values = { suboccupancies.get("suboccupancycode"), suboccupancies.get("description"),
				suboccupancies.get("suboccupancyname") };
		sql = "SELECT * FROM masters.suboccupancies WHERE  LOWER(suboccupancycode)=LOWER(?) OR LOWER(description)=LOWER(?) OR LOWER(suboccupancyname)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);

		if (!exist) {
			if (serviceUserManagementInterface.initsuboccupancies(suboccupancies)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		} 

		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/initusages.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initusages(@RequestBody Map<String, Object> usages) {
		HashMap<String, Object> response = new HashMap<String, Object>();

		// check existance
		String sql = "";
		Object[] values = { usages.get("usagecode"), usages.get("description"), usages.get("usagename") };
		sql = "SELECT * FROM masters.usages WHERE  LOWER(usagecode)=LOWER(?) OR LOWER(description)=LOWER(?) OR LOWER(usagename)=LOWER(?) ";

		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);

		if (!exist) {
			if (serviceUserManagementInterface.initusages(usages)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}

		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/initoccupancies.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> initoccupancies(@RequestBody Map<String, Object> occupancy) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		// check existance
		String sql = "";
		Object[] values = { occupancy.get("occupancycode"), occupancy.get("occupancyname"),
				occupancy.get("occupancyalias") };
		sql = "SELECT * FROM masters.occupancies WHERE  LOWER(occupancycode)=LOWER(?) OR LOWER(occupancyname)=LOWER(?) OR LOWER(occupancyalias)=LOWER(?) ";
		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
		System.out.println("exist::"+exist);
		if (!exist) {
			if (serviceUserManagementInterface.initoccupancy(occupancy)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateoccupancy.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateoccupancy(@RequestBody Occupancies occupancy) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		// check existance
		String sql = "";
		Object[] values = { occupancy.getOccupancycode(), occupancy.getOccupancyname(),
				occupancy.getOccupancyalias()};
		sql = "SELECT * FROM masters.occupancies WHERE  LOWER(occupancycode)=LOWER(?) AND LOWER(occupancyname)=LOWER(?) AND LOWER(occupancyalias)=LOWER(?)  ";
		boolean exist = serviceUserManagementInterface.checkExistance(sql, values);
				if (!exist) {
			if (serviceUserManagementInterface.updateoccupancy(occupancy)) {
				response.put("response", HttpStatus.CREATED);
				response.put("data", 1);
				return ResponseEntity.ok().body(response);
			}
		}
	
		response.put("response", HttpStatus.OK);
		response.put("data", -1);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/listOccupancies.htm")
	public @ResponseBody List<Occupancies> listOccupancies() {
		System.out.println("list occupancies");
		return serviceUserManagementInterface.listOccupancies();
	}

	@GetMapping("/listUsages.htm")
	public @ResponseBody List<Usages> listUsages() {
		System.out.println("listUsages");
		return serviceUserManagementInterface.listUsages();
	}

	@GetMapping("/listFeeMaster.htm")
	public @ResponseBody List<FeeMaster> listFeeMaster() {
		System.out.println("list fee master");
		return serviceUserManagementInterface.listFeeMaster();
	}

	@GetMapping("/listSubOccupancy.htm")
	public @ResponseBody List<SubOccupancies> listSubOccupancy() {
		System.out.println("listSubOccupancyr");
		return serviceUserManagementInterface.listSubOccupancy();
	}

}
