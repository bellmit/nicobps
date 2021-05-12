package obps.controllers;

import java.util.Base64;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import obps.models.EdcrScrutiny;
import obps.services.ServiceEdcrScrutiny;

@Controller
public class ControllerEdcrScrutiny {
	
	@Autowired ServiceEdcrScrutiny edcrscrutiny;
	
	
	@GetMapping(value = "/edcrscrutiny.htm")
	public String scrutinize_Get() {
		System.out.println("edcrscrutiny.htm GET");
		return "edcrScrutiny/edcrscrutiny";
	}
//	@CrossOrigin
//	@PostMapping(value = "/scrutinize_edcr.htm")
//	public @ResponseBody String scrutinize_Post(@RequestBody MultipartFile planFile,@RequestParam String edcrRequest,HttpServletRequest request) {
//		System.out.println("edcrscrutiny.htm POST");
//		String usercode=(String)request.getSession().getAttribute("usercode");		
//		return  edcrscrutiny.Scrutinize( planFile,usercode);
//	}
//	
	@GetMapping(value = "/fetch_edcr.htm", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody EdcrScrutiny fetchEdcrScrutiny( @RequestParam String edcrnumber) {
		System.out.println("fetchEdcrScrutiny POST-----"+edcrnumber);
	
		return   edcrscrutiny.fetch(edcrnumber);
	}
 	 
	@PostMapping(value = "/scrutinize_edcr1.htm",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JSONObject scrutinize_Post( @RequestBody MultipartFile planFile,HttpServletRequest request) {
		System.out.println("edcrscrutiny.htm POST");
		System.out.println(planFile);
		String usercode=(String)request.getSession().getAttribute("usercode");		
		return  edcrscrutiny.Scrutinize(planFile,usercode);
		 
	}
}
