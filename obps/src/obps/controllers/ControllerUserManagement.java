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
import org.springframework.web.bind.annotation.GetMapping;
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

import obps.util.application.ServiceUtilInterface;
import obps.util.common.UtilFile;
import obps.validators.UploadEnclosuresValidatorInterface;
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
	private ServiceUserManagementInterface serviceUserManagementInterface;
	@Autowired
	private UploadEnclosuresValidatorInterface uploadBpaEnclosuersValidatorInterface;
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
		String usercode = (String) request.getSession().getAttribute("usercode");
		if (usercode != null) {			
			data.put("listEnclosures", serviceUtilInterface.listEnclosures(Short.valueOf("1"),Integer.valueOf(usercode)));
		} else {
			data.put("listEnclosures", serviceUtilInterface.listEnclosures(Short.valueOf("1")));
		}				
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
		System.out.println("validate file param controller"+param);
		Log.info("validate file param controller"+param);
		//validate file
		if(uploadBpaEnclosuersValidatorInterface.validateEnclosureDetails(param)) {
			System.out.println("validated file!");
			if (serviceUserManagementInterface.submitEnclosureDetails(param)) {
				return ResponseEntity.ok(new String("The documents have been uploaded successfully."));
			} else {
				return ResponseEntity.badRequest().body(new String("Unable to process request!"));
			}
		}else {
			return ResponseEntity.badRequest().body(new String("Invalid File!Documents could not be uploaded!"));
		}
		
	}

	@RequestMapping(value = "/output.htm", method = RequestMethod.GET)
    public String showFile(HttpServletRequest request,HttpServletResponse response,
    		@RequestParam(value="usercode", required=true) String usercode,
    		@RequestParam(value="enclosurecode", required=true) String enclosurecode)
    {
        String successUrel="output";
        try
        {
    		String sql = "SELECT enclosureimage FROM nicobps.licenseesenclosures WHERE usercode=? AND enclosurecode=?";
    		Object[] criteria = { Integer.valueOf(usercode),Short.valueOf(enclosurecode) };	        
            byte[] binaryFile = serviceUtilInterface.getBytes(sql,criteria);
            //System.out.println("File Size : "+binaryFile.length);
            //writeBytesToFile(binaryFile);
            //response.getOutputStream().write(binaryFile);

            String enclosurename="enclosure"+enclosurecode;
            String ContentType = UtilFile.getFileContentType(binaryFile);
            
            if(ContentType.contentEquals("application/pdf")) {
            	enclosurename+=".pdf";
            }else {
            	enclosurename+=".jpg";
            }
            
            response.setContentType(ContentType);
            response.setHeader("Content-Disposition", "filename="+enclosurename);
            response.setContentLength(binaryFile.length);
            OutputStream os = response.getOutputStream();

            try {
               os.write(binaryFile , 0, binaryFile.length);
            } catch (Exception excp) {
               //handle error
            } finally {
                os.close();
            }         
        }catch(Exception e) {
        	System.out.println("Exception :: "+e);
            //successUrel="rdirect:error.jsp";           
        }
        return successUrel;
    }    	
	
    private static void writeBytesToFile(byte[] bytes)throws IOException {
    	
      String fileOutput="D:\\enc";
      String ContentType = UtilFile.getFileContentType(bytes);
      
      System.out.println("ContentType : "+ContentType);
      if(ContentType.contentEquals("application/pdf")) {
    	  fileOutput+=".pdf";
      }else {
    	  fileOutput+=".jpg";
      }    	
    	
    	 System.out.println("File Size :: "+bytes.length);
//        try (FileOutputStream fos = new FileOutputStream(fileOutput)) {
//                fos.write(bytes);
//        }    	 
    	 Path path = Paths.get(fileOutput);
         Files.write(path, bytes);    	     	 
    }	
	
	// =================================Change Password====================================//
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
