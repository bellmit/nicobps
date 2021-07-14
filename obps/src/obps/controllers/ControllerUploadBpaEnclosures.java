package obps.controllers;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.Map;

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
import obps.validators.ValidateLicenseEnclosures;

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
	private ServiceUserManagementInterface serviceUserManagementInterface;

	@Autowired
	private ValidateLicenseEnclosures vle;

//	@RequestMapping("/bpauploadenc.htm")
//	public String uploadbpaenclosuresext(Model model, @RequestParam String applicationcode) {
//		model.addAttribute("applicationcode", applicationcode);
//		return PARENT_URL_MAPPING.concat("/uploadbpaenclosuresext");
//	}

//	@ModelAttribute("bpaEnclosures") BpaEnclosures bpaEnclosures,
//	BindingResult result

	@RequestMapping(value = "/bpauploadenc.htm")
	public String uploadbpaenclosuresint(ModelMap model, @ModelAttribute("BpaEnclosures") BpaEnclosures bpaEnclosures,
			BindingResult result, @RequestParam String applicationcode,HttpServletRequest request) {
		request.getSession().setAttribute("applicationcode", applicationcode);
		bpaEnclosures.setApplicationcode(applicationcode);
		model.addAttribute("successMsg", "");
		model.addAttribute("enclosuresList",
				serviceUtilInterface.listBpaEnclosures(Short.valueOf("2"), applicationcode));
		return PARENT_URL_MAPPING.concat("/uploadbpaenclosuresext");
	}

	@RequestMapping(value = "submitbpaenclosures.htm", params = "_submit", method = RequestMethod.POST)
	public String submitbpaenclosures(ModelMap model,
			@ModelAttribute("licenseesenclosures") BpaEnclosures bpaenclosures, BindingResult result,
			HttpServletRequest request) {		
		bpaenclosures.setApplicationcode((String)request.getSession().getAttribute("applicationcode"));
		
		Userlogin user = (Userlogin) request.getSession().getAttribute("user");
		//String successurl = "redirect:bpasiteinspection.htm?applicationcode=" + bpaenclosures.getApplicationcode();
		String successurl = "redirect:bpatrackstatus.htm?applicationcode=" + bpaenclosures.getApplicationcode();
		String usercode = (String) request.getSession().getAttribute("usercode");
		model.addAttribute("successMsg", "");	
		String appenclosurecode = ServiceUploadBpaEnclosuersInterface.getMaxAppEnclosureCode() + "";
		
		bpaenclosures.setAppenclosurecode(appenclosurecode);
		bpaenclosures.setUsercode(usercode);
		bpaenclosures.setSessioncaptcha((String) request.getSession().getAttribute("CAPTCHA_KEY"));
		
//			vle.validate(bpaenclosures, result);
		if (!result.hasErrors()) 
		{
			String afrcode = serviceUserManagementInterface.getMaxAfrCode() + "";
			bpaenclosures.setAfrcode(afrcode);
			System.out.println("submitbpaenclosures.htm"  +bpaenclosures.toString());
			if (ServiceUploadBpaEnclosuersInterface.submitBpaEnclosureDetails(bpaenclosures)) {
				model.addAttribute("successMsg", "The documents have been uploaded successfully.");
			} else {
				model.addAttribute("successMsg",
						"Sorry, but we are unable to process the request at the moment. Please try again later.!");
			}
		}
		

		return successurl;

	}
	@RequestMapping(value = "/outputbpa.htm", method = RequestMethod.GET)
	public String showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "usercode", required = true) String applicationcode,
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
}