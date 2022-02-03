package obps.controllers;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.models.BpaEnclosures;
import obps.models.LicenseesEnclosures;
import obps.models.Userlogin;
import obps.services.ServiceUploadBpaEnclosuersInterface;
import obps.services.ServiceUserManagementInterface;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.UtilFile;
import obps.util.notifications.ServiceNotification;
import obps.validators.ValidateBpaEnclosures;

//@RestController
@Controller
@Configuration
@PropertySource("classpath:application.properties")
public class ControllerUploadBpaEnclosures {
	private static final Logger LOG = Logger.getLogger(ControllerUploadBpaEnclosures.class.toGenericString());

	private static final String PARENT_URL_MAPPING = "/bpa";
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	@Autowired
	private ServiceUploadBpaEnclosuersInterface ServiceUploadBpaEnclosuersInterface;

	@Autowired
	private ServiceNotification serviceNotification;

	@Autowired
	private ServiceUserManagementInterface serviceUserManagementInterface;

	@Autowired
	private ValidateBpaEnclosures vle;

//	@RequestMapping("/bpauploadenc.htm")
//	public String uploadbpaenclosuresext(Model model, @RequestParam String applicationcode) {
//		model.addAttribute("applicationcode", applicationcode);
//		return PARENT_URL_MAPPING.concat("/uploadbpaenclosuresext");
//	}

//	@ModelAttribute("bpaEnclosures") BpaEnclosures bpaEnclosures,
//	BindingResult result

	@RequestMapping(value = "/bpauploadenc.htm")
	public String uploadbpaenclosuresint(ModelMap model, @ModelAttribute("BpaEnclosures") BpaEnclosures bpaEnclosures,
			BindingResult result, @RequestParam String applicationcode, HttpServletRequest request) {
		request.getSession().setAttribute("applicationcode", applicationcode);
		bpaEnclosures.setApplicationcode(applicationcode);
		model.addAttribute("successMsg", "");
		model.addAttribute("enclosuresList",
				serviceUtilInterface.listBpaEnclosures(Short.valueOf("2"), applicationcode));
		return PARENT_URL_MAPPING.concat("/bpauploadenc");
	}

	@RequestMapping(value = "submitbpaenclosures.htm",  method = RequestMethod.POST)
	public String submitbpaenclosures(ModelMap model,
			@ModelAttribute("licenseesenclosures") BpaEnclosures bpaenclosures, BindingResult result,
			HttpServletRequest request) {

		Userlogin user = (Userlogin) request.getSession().getAttribute("user");

		String successurl = "bpauploadenc.htm";
		String usercode = (String) request.getSession().getAttribute("usercode");
		model.addAttribute("successMsg", "");
		String appenclosurecode = ServiceUploadBpaEnclosuersInterface.getMaxAppEnclosureCode() + "";

		bpaenclosures.setApplicationcode((String) request.getSession().getAttribute("applicationcode"));
		bpaenclosures.setAppenclosurecode(appenclosurecode);
		bpaenclosures.setUsercode(usercode);
		bpaenclosures.setSessioncaptcha((String) request.getSession().getAttribute("CAPTCHA_KEY"));
		System.out.println("application code =================== " + bpaenclosures.getApplicationcode());
		vle.validate(bpaenclosures, result);
		System.out.println("result :: "+result.toString());
		if (!result.hasErrors()) {
			String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
			bpaenclosures.setAfrcode(afrcode);
			System.out.println("submitbpaenclosures.htm" + bpaenclosures.toString());
			if (ServiceUploadBpaEnclosuersInterface.submitBpaEnclosureDetails(bpaenclosures)) {
				model.addAttribute("successMsg", "The documents have been uploaded successfully.");
				successurl = "redirect:bpatrackstatus.htm?applicationcode=" + bpaenclosures.getApplicationcode();

				Map<String, Object> userdet = serviceUtilInterface
						.getUserDetails(Integer.valueOf(bpaenclosures.getUsercode()));
				String applicationcode = bpaenclosures.getApplicationcode();

				if (userdet != null && applicationcode != null) {
					if (!userdet.isEmpty() && !applicationcode.isEmpty()) {
						try {
							String emailid = userdet.get("username").toString();
							String mobileno = userdet.get("mobileno").toString();

							serviceNotification.sentNotification(2, "BPA_APPLY", mobileno, emailid,
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

			} else {
				model.addAttribute("successMsg",
						"Sorry, but we are unable to process the request at the moment. Please try again later.!");
			}
		}
		if (usercode != null) {
			model.addAttribute("enclosuresList", serviceUtilInterface.listBpaEnclosures(Short.valueOf("2"),
					(String) request.getSession().getAttribute("applicationcode")));
		}

		return successurl;

	}

	@RequestMapping(value = "/outputbpa.htm", method = RequestMethod.GET)
	public String showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "applicationcode", required = true) String applicationcode,
			@RequestParam(value = "enclosurecode", required = true) String enclosurecode) {
		String successUrel = "output";
		try {
			String sql = "SELECT enclosureimage FROM nicobps.bpaenclosures WHERE applicationcode=? AND enclosurecode=?";
			Object[] criteria = { Integer.valueOf(applicationcode), Short.valueOf(enclosurecode) };
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

	@GetMapping(value = { "/dayendstat.htm", "/dayendStat.htm" })
	public String dayendstat(Model model, HttpServletRequest request) {
		model.addAttribute("Listofficecode", serviceUtilInterface.listUserOffices());
		return "dayendstatement";
	}
}