package obps.controllers;

import java.io.OutputStream;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import obps.models.EdcrScrutiny;
import obps.services.ServiceEdcrScrutiny;
import obps.util.application.ServiceUtilInterface;
import obps.util.common.UtilFile;

@Controller
public class ControllerEdcrScrutiny {

	@Autowired
	ServiceEdcrScrutiny edcrscrutiny;
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;

	@GetMapping(value = "/edcrscrutiny.htm")
	public String scrutinize_Get() {
		System.out.println("edcrscrutiny.htm GET");
		return "edcrScrutiny/edcrscrutiny";
	}

	@GetMapping(value = "/fetch_edcr_usercd.htm", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EdcrScrutiny> fetchEdcrScrutiny_usercode(HttpServletRequest request) {
		System.out.println("fetch_edcr_usercd GET");
		String usercode = (String) request.getSession().getAttribute("usercode");
		return edcrscrutiny.fetch_usercd(usercode);
	}

	@GetMapping(value = "/fetch_edcr.htm", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EdcrScrutiny fetchEdcrScrutiny(@RequestParam String edcrnumber) {
		System.out.println("fetchEdcrScrutiny POST-----" + edcrnumber);
		return edcrscrutiny.fetch(edcrnumber);
	}

	@PostMapping(value = "/scrutinize_edcr.htm", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONObject scrutinize_Post(@RequestBody MultipartFile planFile,
			@RequestParam String OfficeCode, @RequestParam String stateid, @RequestParam String tenantid,@RequestParam String originalfilename,
			HttpServletRequest request) {
		System.out.println("edcrscrutiny.htm POST");
		System.out.println("original file name "+originalfilename );
		String usercode = (String) request.getSession().getAttribute("usercode");
		return edcrscrutiny.Scrutinize(planFile, usercode, OfficeCode, stateid, tenantid,originalfilename);
	}

//	@RequestMapping(value = "/ScrutinyshowFile.htm", method = RequestMethod.GET)
//	public @ResponseBody String showFile(HttpServletRequest request, HttpServletResponse response,
//			@RequestParam(value = "edcrnumber", required = true) String edcrnumber) {
//		String successUrel = "output";
//		try {
//			String sql = "select scrutinyreport from nicobps.edcrscrutiny where edcrnumber=?";
//			Object[] criteria = { edcrnumber };
//			byte[] binaryFile = serviceUtilInterface.getBytes(sql, criteria);
//
//			String planFile = "planFile-" + edcrnumber;
//			String ContentType = UtilFile.getFileContentType(binaryFile);
//
//			if (ContentType.contentEquals("application/pdf")) {
//				planFile += ".pdf";
//			} else {
//				planFile += ".jpg";
//			}
//
//			response.setContentType(ContentType);
//			response.setHeader("Content-Disposition", "filename=" + planFile);
//			response.setContentLength(binaryFile.length);
//			OutputStream os = response.getOutputStream();
//
//			try {
//				os.write(binaryFile, 0, binaryFile.length);
//			} catch (Exception excp) {
//				// handle error
//			} finally {
//				os.close();
//			}
//		} catch (Exception e) {
//			System.out.println("Exception :: " + e);
//			// successUrel="rdirect:error.jsp";
//		}
//		return successUrel;
//	}

	@RequestMapping(value = "/ScrutinyshowFile.htm", method = RequestMethod.GET)
	public @ResponseBody String showFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "edcrnumber", required = true) String edcrnumber) {
	
		byte[] binaryFile = null;
		try {
			String sql = "select scrutinyreport from nicobps.edcrscrutiny where edcrnumber=?";
			Object[] criteria = { (edcrnumber) };
			binaryFile = serviceUtilInterface.getBytes(sql, criteria);
			System.out.println(binaryFile);
			System.out.println("iframe src=\"data:application/pdf;base64,"+Base64.getEncoder().encodeToString("adasd".getBytes())+"\"></iframe");
			
		} catch (Exception e) {
			System.out.println("Exception :: " + e);

		}
		return (binaryFile != null)? "<iframe src=\"data:application/pdf;base64,"+Base64.getEncoder().encodeToString(binaryFile)+"\" height=\"100%\" width=\"100%\"></iframe>" : null; 
	}
}
