package obps.controllers;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import obps.models.EdcrScrutiny;
import obps.services.ServiceEdcrScrutiny;

@Controller
public class ControllerEdcrScrutiny {

	@Autowired
	ServiceEdcrScrutiny edcrscrutiny;
	

	
	@GetMapping(value = "/edcrscrutiny.htm")
	public String scrutinize_Get() {
		System.out.println("edcrscrutiny.htm GET");
	
		return "edcrScrutiny/edcrscrutiny";
	}

	@GetMapping(value = "/fetch_edcr_usercd.htm", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<EdcrScrutiny> fetchEdcrScrutiny_usercode( HttpServletRequest request) {
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
	public @ResponseBody JSONObject scrutinize_Post(@RequestBody MultipartFile planFile,@RequestParam String OfficeCode,@RequestParam String stateid,@RequestParam String tenantid, HttpServletRequest request) {
		System.out.println("edcrscrutiny.htm POST");
		System.out.println(stateid);
		System.out.println(tenantid);
		String usercode = (String) request.getSession().getAttribute("usercode");
	 
		return  edcrscrutiny.Scrutinize(planFile,usercode,OfficeCode,stateid,tenantid);

	}
}
