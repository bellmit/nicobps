package obps.controllers;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import obps.services.ServicePrintPermitInterface;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.UtilFile;
import obps.validators.PrintPermitValidator;

@Controller
public class ControllerPrintPermit {

	@Autowired
	private ServicePrintPermitInterface serviceprintpermit;
	@Autowired
	private PrintPermitValidator printpermitvalidate;
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	
	@GetMapping(value = { "/printpermit.htm", "/printPermit.htm" })
	public String printpermit(@RequestParam(value = "applicationcode", required = false) String applicationcode,
			Model model) {
		if (applicationcode != null) {
			model.addAttribute("applicationcode", applicationcode);
		}
		return "bpa/printpermit";

	}

	@PostMapping(value = "/getPermitList.htm")
	public @ResponseBody List<Map<String, Object>> getpermitList(@RequestParam Map<String, String> params) {
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		Map<String, Object> resp = new HashMap<String, Object>();
		try {

			String validateparams = printpermitvalidate.validate_params(params);
			if (validateparams.equals("1")) {

				response = serviceprintpermit.getPermitList(params.get("applicationcode"), params.get("permitnumber"),
						params.get("edcrnumber"), params.get("ownername"), params.get("fromentrydate"),
						params.get("toentrydate"), params.get("criteria"));

			} else {
//				resp.put("error", validateparams);
				resp.put("error", "Please check your inputs!!");
				response.add(resp);
			}
		} catch (Exception e) {

			resp.put("error", "Exception encountered while getting list");
			response.add(resp);
		}

		return response;

	}

	@RequestMapping(value = "/outputsigneddoc.htm", method = RequestMethod.GET)
	public String showFilesigneddoc(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "permitnumber", required = true) String permitnumber) {

		String successUrel = "output";
		try {
			String sql = "select enclosureimage from " + "nicobps.bpaapproveapplications BA "
					+ "inner join nicobps.bpaenclosures BE on BE.applicationcode=BA.applicationcode "
					+ "inner join masters.modulesenclosures ME on ME.enclosurecode=BE.enclosurecode "
					+ "where ME.processcode=12 AND ME.modulecode=2 AND BA.permitnumber=?";
			System.out.println("permitnumber === " + permitnumber);
			Object[] criteria = { String.valueOf(permitnumber) };
			byte[] binaryFile = serviceUtilInterface.getBytes(sql, criteria);
			System.out.println("File Size : " + binaryFile.length);
			// writeBytesToFile(binaryFile);
			// response.getOutputStream().write(binaryFile);

//			String enclosurename = "enclosure" + enclosurecode;
			
			String ContentType = UtilFile.getFileContentType(binaryFile);
//
//			if (ContentType.contentEquals("application/pdf")) {
//				enclosurename += ".pdf";
//			} else {
//				enclosurename += ".jpg";
//			}

			response.setContentType(ContentType);
			response.setHeader("Content-Disposition", "inline; filename=Enclosures.pdf");
			response.setHeader("Cache-Control", "no-cache");
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
