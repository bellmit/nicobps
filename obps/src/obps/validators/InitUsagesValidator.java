package obps.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class InitUsagesValidator {
	public String validateInitUsages(Map<String, Object> param) {
		String response="";
		Matcher m;
		boolean b=false;
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		Pattern p1 = Pattern.compile("[^A-Za-z_ 0-9]");
		String suboccupancycode="",usagecode="",usagename="",description="";
		if(param.get("suboccupancycode")!=null) {
			suboccupancycode=((String) param.get("suboccupancycode")).trim();
			
				
			if(suboccupancycode.length()>10) {
				response= "suboccupancycodesizeerror";
				return response;
			}
		}else {
			response= "suboccupancycodenull";
			return response;
		}
		if(param.get("usagecode")!=null) {
			usagecode=((String) param.get("usagecode")).trim();
			
				
			if(usagecode.length()>20) {
				response= "usagecodesizeerror";
				return response;
			}
		}else {
			response= "usagecodenull";
			return response;
		}
		if(param.get("usagename")!=null) {
			usagename=((String) param.get("usagename")).trim();
			
				
			if(usagename.length()>255) {
				response= "usagenamesizeerror";
				return response;
			}
		}else {
			response= "usagenamenull";
			return response;
		}
		if(param.get("description")!=null) {
			description=((String) param.get("description")).trim();
			
			if(description.length()>250) {
				response= "descriptionsizeerror";
				return response;
			}
		}
		else {
			response= "descriptionnull";
			return response;
		}
		return response;
	}
}
