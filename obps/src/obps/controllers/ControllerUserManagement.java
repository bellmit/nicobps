package obps.controllers;

import java.util.Map;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import obps.util.application.CommonMap;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.UtilFile;
import obps.util.common.Utilty;

import obps.util.notifications.Notification;
import obps.util.notifications.ServiceEmailApi;
import obps.util.notifications.ServiceNotification;
import obps.util.notifications.ServiceSms;


import obps.validators.UserManagementValidator;


import obps.validators.ValidateLicenseEnclosures;
import obps.daos.DaoEnclosureManagementInterface;
import obps.daos.DaoUserManagementInterface;
import obps.models.AppEnclosures;
import obps.models.LicenseesEnclosures;
import obps.models.Pageurls;
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
	private DaoEnclosureManagementInterface daoEnclosureManagementInterface;
	@Autowired
	private ServiceUserManagementInterface serviceUserManagementInterface;


	@Autowired
	private ServiceNotification serviceNotification;
	
	@Autowired
	private UserManagementValidator userManagementValidator;

	@Resource
	private Environment environment;	
	
	@Autowired
	private ValidateLicenseEnclosures vle;

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attr.getRequest().getSession(true);
	}

	// =================================Registration====================================//
	@RequestMapping("/signup.htm")
	public String signup() {
		//Notification notification = serviceNotification.notificationDetails(Integer.valueOf("1"), "REGISTRATION");
		
		//notification.setRecipientMobileno("9366554970");
		//notification.setRecipientEmailid("avijitdebnath@gmail.com");
		
		//ServiceSms ss = new ServiceSms();
		//ss.sendSingleSMS(notification);
		//ServiceEmailApi se = new ServiceEmailApi();
		
		//se.sendEmails(notification);
		
		
		return "signup";
	}

	@GetMapping(value = "/initSignupForm.htm")
	public @ResponseBody Map<String, Object> initSignupForm(HttpServletRequest request) {
		String issms = environment.getProperty("app.prop.issms");
		String isemail = environment.getProperty("app.prop.isemail");

		Map<String, Object> data = new LinkedHashMap<>();
		data.put("listLicenseetypes", serviceUtilInterface.listLicenseetypes());
		data.put("listStates", serviceUtilInterface.listStates());
		data.put("listDistricts", serviceUtilInterface.listDistricts());
		data.put("listLicenseesregistrationsm", serviceUtilInterface.listLicenseesregistrationsm());
		data.put("issms", issms);
		data.put("isemail", isemail);
		return data;
	}
	
	@PostMapping(value = "/resendOTP.htm")
	public @ResponseBody String resendOTP(HttpServletRequest request) 
	{			
		String issms = environment.getProperty("app.prop.issms");
		String isemail = environment.getProperty("app.prop.isemail");	
		
		if (issms.equals("Y")) {
			Integer mobileotp = Utilty.getRandomNumber();
			request.getSession().setAttribute("mobileotp", mobileotp.toString());
			// Send SMS
			//System.out.println("mobileotp : "+mobileotp);
		}
		if (isemail.equals("Y")) {
			Integer emailotp = Utilty.getRandomNumber();
			request.getSession().setAttribute("emailotp", emailotp.toString());
			// Send Email
			//System.out.println("emailotp : "+emailotp);
		}		
		return "OTP Sent";
	}
	

	@PostMapping("/submitSignupDetails.htm")
	// public ResponseEntity<?> submitProfilePersonalDetails(@RequestBody
	// Map<String,Object> param,HttpServletRequest request)
	public ResponseEntity<?> submitProfilePersonalDetails(@RequestParam Map<String, Object> param,HttpServletRequest request) 
	{
		// for (Map.Entry<String, Object> entry : param.entrySet()){
		// System.out.println(entry.getKey() +" ::: " + entry.getValue());
		// }
		// ==========================================================================//
		String usersessioncaptcha = (String) request.getSession().getAttribute("CAPTCHA_KEY");
		String userresponsecaptcha = (String) param.get("userresponsecaptcha");
		String isotp = (String) param.get("isotp");
		String issms = environment.getProperty("app.prop.issms");
		String isemail = environment.getProperty("app.prop.isemail");

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
		
		Notification notification = serviceNotification.notificationDetails(Integer.valueOf("1"), "REGISTRATION");
		
		if (issms.equals("Y") || isemail.equals("Y")) {
			if (isotp.equals("N")) {
				if (issms.equals("Y")) {
					Integer mobileotp = Utilty.getRandomNumber();
					request.getSession().setAttribute("mobileotp", mobileotp.toString());
					// Send SMS
				}
				if (isemail.equals("Y")) {
					Integer emailotp = Utilty.getRandomNumber();
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
					request.getSession().setAttribute("licenseetypecode", (String) param.get("licenseetypecode"));
					// return ResponseEntity.ok(new String("Details submitted successfully!"));
					return ResponseEntity.ok(new String("1"));
				} else {
					return ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later."));
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
				return ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later."));
			}
		}

	}

	// =================================Upload
	// Enclosures====================================//
	@RequestMapping("/uploadenclosuresext.htm")
	public String uploadenclosuresext(ModelMap model,
			@ModelAttribute("licenseesenclosures") LicenseesEnclosures licenseesenclosures, BindingResult result,
			HttpServletRequest request) {
		model.addAttribute("successMsg", "");
		model.addAttribute("successNotUploadedMsg", "");
		String usercode = (String) request.getSession().getAttribute("usercode");
		String licenseetypecode = (String) request.getSession().getAttribute("licenseetypecode");
		if (licenseetypecode != null && usercode != null) {
			// model.addAttribute("enclosuresList",serviceUtilInterface.listEnclosures(Short.valueOf("1"),Integer.valueOf(usercode)));
			model.addAttribute("enclosuresList", serviceUtilInterface.listEnclosures(Short.valueOf("1"),
					Integer.valueOf(usercode), Short.valueOf(licenseetypecode)));
		}
		return "uploadenclosuresext";
	}

	@RequestMapping("/uploadenclosuresint.htm")
	public String uploadenclosuresint(ModelMap model,
			@ModelAttribute("licenseesenclosures") LicenseesEnclosures licenseesenclosures, BindingResult result,
			HttpServletRequest request) {
		model.addAttribute("successMsg", "");
		model.addAttribute("successNotUploadedMsg", "");
		String usercode = (String) request.getSession().getAttribute("usercode");
		String licenseetypecode = (String) request.getSession().getAttribute("licenseetypecode");
		if (licenseetypecode != null && usercode != null) {
			// model.addAttribute("enclosuresList",serviceUtilInterface.listEnclosures(Short.valueOf("1"),Integer.valueOf(usercode)));
			model.addAttribute("enclosuresList", serviceUtilInterface.listEnclosures(Short.valueOf("1"),
					Integer.valueOf(usercode), Short.valueOf(licenseetypecode)));
			List<CommonMap> listEnclosuresNotUploades = serviceUtilInterface.listEnclosuresNotUploades(Short.valueOf("1"), Integer.valueOf(usercode), Short.valueOf(licenseetypecode));			
			model.addAttribute("listEnclosuresNotUploades",listEnclosuresNotUploades );
			//System.out.println("listEnclosuresNotUploades.size() : "+listEnclosuresNotUploades.size());
			if(listEnclosuresNotUploades.size()>0) {
				model.addAttribute("successNotUploadedMsg", "Please upload the remaining documents indicated as mandatory before being allowed to empanel with ULBs");
			}
		}
		return "uploadenclosuresint";
	}

	@RequestMapping(value = "submitLicenseesenclosures.htm", params = "_submit", method = RequestMethod.POST)
	public String submitLicenseesenclosures(ModelMap model,
			@ModelAttribute("licenseesenclosures") LicenseesEnclosures licenseesenclosures, BindingResult result,
			HttpServletRequest request) {
		Userlogin user = (Userlogin) request.getSession().getAttribute("user");
		String successurl = user != null ? "uploadenclosuresint" : "uploadenclosuresext";

		String usercode = (String) request.getSession().getAttribute("usercode");
		String licenseetypecode = (String) request.getSession().getAttribute("licenseetypecode");
		model.addAttribute("successMsg", "");
		model.addAttribute("successNotUploadedMsg", "");
		if (usercode != null && licenseetypecode != null) 
		{
			licenseesenclosures.setUsercode(usercode);
			licenseesenclosures.setLicenseetypecode(usercode);
			licenseesenclosures.setSessioncaptcha((String) request.getSession().getAttribute("CAPTCHA_KEY"));
			vle.validate(licenseesenclosures, result);
			if (!result.hasErrors()) {
				String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
				licenseesenclosures.setAfrcode(afrcode);

				if (serviceUserManagementInterface.submitLicenseesenclosures(licenseesenclosures)) 
				{
					
					List<CommonMap> listEnclosuresNotUploades = serviceUtilInterface.listEnclosuresNotUploades(Short.valueOf("1"), Integer.valueOf(usercode), Short.valueOf(licenseetypecode));			
					String successMsg="";
					if(user != null) {
						if(listEnclosuresNotUploades.size()==0) {
							successMsg="Documents uploaded successfully, please register with UBLs";							
						}else {
							successMsg="Documents uploaded successfully, please upload the remaining documents indicated as mandatory before being allowed to empanel with ULBs";
						}
					}else {
						if(listEnclosuresNotUploades.size()==0) {
							successMsg="Documents uploaded successfully, please login and register with UBLs";							
						}else {
							successMsg="Documents uploaded successfully, please upload the remaining documents indicated as mandatory before being allowed to empanel with ULBs";
						}						
					}										
					model.addAttribute("successMsg",successMsg);
					//model.addAttribute("successMsg", "The documents have been uploaded successfully.");
				} else {
					model.addAttribute("successMsg", "Sorry, but we are unable to process the request at the moment. Please try again later.!");
				}
			}
			if (licenseetypecode != null && usercode != null) {
				model.addAttribute("enclosuresList", serviceUtilInterface.listEnclosures(Short.valueOf("1"),
						Integer.valueOf(usercode), Short.valueOf(licenseetypecode)));
			}
		} else {
			successurl = "redirect:login.htm";
		}

		return successurl;

	}

	/*
	 * @GetMapping(value = "/initUploadEnclosuresForm.htm") public @ResponseBody
	 * Map<String, Object> initUploadEnclosuresForm(HttpServletRequest request) {
	 * Map<String, Object> data = new LinkedHashMap<>(); String usercode = (String)
	 * request.getSession().getAttribute("usercode"); if (usercode != null) {
	 * data.put("listEnclosures",
	 * serviceUtilInterface.listEnclosures(Short.valueOf("1"),Integer.valueOf(
	 * usercode))); } else { data.put("listEnclosures",
	 * serviceUtilInterface.listEnclosures(Short.valueOf("1"))); } return data; }
	 * 
	 * @PostMapping("/submitEnclosureDetails.htm") public ResponseEntity<?>
	 * submitEnclosureDetails(@RequestParam Map<String, Object> param,
	 * HttpServletRequest request) { String usersessioncaptcha = (String)
	 * request.getSession().getAttribute("CAPTCHA_KEY"); String userresponsecaptcha
	 * = (String) param.get("userresponsecaptcha");
	 * 
	 * if (usersessioncaptcha == null || userresponsecaptcha == null ||
	 * !usersessioncaptcha.trim().equals(userresponsecaptcha.trim())) { return
	 * ResponseEntity.badRequest().body(new
	 * String("Please check your entered captcha!")); }
	 * 
	 * String usercode = (String) request.getSession().getAttribute("usercode");
	 * 
	 * String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
	 * param.put("afrcode", afrcode);
	 * 
	 * if (usercode != null) { param.put("usercode", usercode); } else { return
	 * ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later.")); }
	 * System.out.println("validate file param controller"+param);
	 * Log.info("validate file param controller"+param); //validate file
	 * if(uploadBpaEnclosuersValidatorInterface.validateEnclosureDetails(param)) {
	 * System.out.println("validated file!"); if
	 * (serviceUserManagementInterface.submitEnclosureDetails(param)) { return
	 * ResponseEntity.ok(new
	 * String("The documents have been uploaded successfully.")); } else { return
	 * ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later.")); }
	 * }else { return ResponseEntity.badRequest().body(new
	 * String("Invalid File!Documents could not be uploaded!")); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/output.htm", method = RequestMethod.GET)
	public String showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "usercode", required = true) String usercode,
			@RequestParam(value = "enclosurecode", required = true) String enclosurecode) {
		String successUrel = "output";
		try {
			String sql = "SELECT enclosureimage FROM nicobps.licenseesenclosures WHERE usercode=? AND enclosurecode=?";
			Object[] criteria = { Integer.valueOf(usercode), Short.valueOf(enclosurecode) };
			byte[] binaryFile = serviceUtilInterface.getBytes(sql, criteria);
			// System.out.println("File Size : "+binaryFile.length);
			// writeBytesToFile(binaryFile);
			// response.getOutputStream().write(binaryFile);

			String enclosurename = "enclosure" + enclosurecode;
			String ContentType = UtilFile.getFileContentType(binaryFile);

			if (ContentType.contentEquals("application/pdf")) {
				enclosurename += ".pdf";
			} else {
				enclosurename += ".jpg";
			}

			response.setContentType(ContentType);
			response.setHeader("Content-Disposition", "filename=" + enclosurename);
			response.setContentLength(binaryFile.length);
			OutputStream os = response.getOutputStream();

			try {
				os.write(binaryFile, 0, binaryFile.length);
			} catch (Exception excp) {
				// handle error
			} finally {
				os.close();
			}
		} catch (Exception e) {
			System.out.println("Exception :: " + e);
			// successUrel="rdirect:error.jsp";
		}
		return successUrel;
	}

	private static void writeBytesToFile(byte[] bytes) throws IOException {

		String fileOutput = "D:\\enc";
		String ContentType = UtilFile.getFileContentType(bytes);

		System.out.println("ContentType : " + ContentType);
		if (ContentType.contentEquals("application/pdf")) {
			fileOutput += ".pdf";
		} else {
			fileOutput += ".jpg";
		}

		System.out.println("File Size :: " + bytes.length);
//        try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
//                fos.write(bytes);
//        }    	 
		Path path = Paths.get(fileOutput);
		Files.write(path, bytes);
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
			return ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later."));
		}

		if (serviceUserManagementInterface.updatePassword(param)) {
			return ResponseEntity.ok(new String("Password updated successfully!"));
		} else {
			return ResponseEntity.badRequest().body(new String("Sorry, but we are unable to process the request at the moment. Please try again later."));
		}
	}

	@GetMapping("/createuser.htm")
	public String createuser(Model model) {
		model.addAttribute("officeList", serviceUtilInterface.listUserOffices());
		return "initialization/createuser";
	}

	@PostMapping(value = "/createuser.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> createUser(@RequestBody Map<String, Object> user) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<CommonMap> officelist = serviceUtilInterface.listUserOffices();
//		System.out.println((Integer) user.get("officecode"));
		if (!session().getAttribute("usercode").equals("1")) {
			boolean exist = false;
			for (CommonMap c : officelist) {
				if (c.getKey().equals(user.get("officecode").toString())) {
					exist = true;
					break;
				}
			} 
			if (!exist) {
				response.put("code", 400);
				response.put("msg", "Not Authorized to create user from selected office.");
				return ResponseEntity.ok().body(response);
			}
		}
		String usercode ="";
		if(serviceUserManagementInterface.getMaxUsercode()!=null)
			usercode = serviceUserManagementInterface.getMaxUsercode() + "";
		user.put("usercode", usercode);
		user.put("usertype", "BACKEND_USER");
		
		String validate=userManagementValidator.validateCreateUser(user);
		System.out.println("Validate"+validate);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
		String username=((String) user.get("username")).trim();
		String sql = "Select count(*) from  nicobps.userlogins where LOWER(username)=LOWER(?)";
		Object[] values = {username};
		boolean exist = daoEnclosureManagementInterface.checkExistance(sql, values);
		System.out.println("easdasd"+exist);
		if(exist)
			response.put("data", "exist");
		else {
			
				
			if (serviceUserManagementInterface.createUser(user)) {
				response.put("code", 200);
				response.put("data", "Success");
				response.put("msg", "");
				return ResponseEntity.ok().body(response);
			}else {
			response.put("code", 200);
			response.put("data", "Error");
			return ResponseEntity.ok().body(response);
			}
		}
		
		response.put("code", 200);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/updateuser.htm", consumes = "application/json")
	public ResponseEntity<HashMap<String, Object>> updateUser(@RequestBody Map<String, Object> user) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String validate=userManagementValidator.validateCreateUser(user);
		System.out.println("Validate"+validate);
		if(validate!="") {
			response.put("code", 200);
			response.put("data", validate);
			return ResponseEntity.ok().body(response);
		}
			
		if (serviceUserManagementInterface.updateUser(user)) {
			response.put("code", 200);
			response.put("data", "Success");
			response.put("msg", "");
			return ResponseEntity.ok().body(response);
		}else {
		response.put("code", 200);
		response.put("data", "Error");
		return ResponseEntity.ok().body(response);
		}
		
	}

	@GetMapping("/listOfficeUsers.htm")
	public @ResponseBody List<Userlogin> listOfficeUsers(Integer officecode) {

		return serviceUserManagementInterface.listOfficeUsers(officecode);
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
	public String accesscontrol(Model model) {
		model.addAttribute("officeList", serviceUtilInterface.listUserOffices());
		return "initialization/accesscontrol";
	}

	@GetMapping(value = "/listUserAndMappedPages.htm")
	public @ResponseBody List<Userlogin> listUserAndMappedPages(Integer officecode) {

		return serviceUserManagementInterface.listUserAndMappedPages(officecode);
	}

	@PostMapping(value = "/saveUserpages.htm")
	public @ResponseBody String saveUserpages(@RequestBody List<Map<String, Object>> userpages) {
		String response="";
		
		String validate= userManagementValidator.validateAccessControl(userpages);
		if(validate!="") {
			response=validate;
		}else {
		response = serviceUserManagementInterface.saveUserpages(userpages);
		}
		return response;
	}
	
	@GetMapping("/userwards.htm")
	public String extendstakeholdervalidity(Model model, HttpServletRequest req,
			@RequestParam Map<String, String> params) {

		model.addAttribute("offices", serviceUtilInterface.listUserOffices());

		return "initialization/userwards";
	}
}
