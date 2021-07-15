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
		Pattern p1 = Pattern.compile("[^A-Za-z_ 0-9\\-]");
		String pattern="[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]";
		Pattern p2 = Pattern.compile(pattern); 
		String suboccupancycode="",usagecode="",usagename="",description="";
		if(param.get("suboccupancycode")!=null) {
			suboccupancycode=((String) param.get("suboccupancycode")).trim();
			m= p1.matcher(suboccupancycode);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Sub Occupancy Code are - _";
				return response;
			}
				
			if(suboccupancycode.length()>10) {
				response= "Sub Occupancy Code Cannot be more than 10 characters";
				return response;
			}
		}else {
			response= "Sub Occupancy Code Cannot Be Null";
			return response;
		}
		if(param.get("usagecode")!=null) {
			usagecode=((String) param.get("usagecode")).trim();
			m= p1.matcher(usagecode);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Usage Code are - _";
				return response;
			}
				
			if(usagecode.length()>20) {
				response= "Usage Code Cannot be more than 20 characters";
				return response;
			}
		}else {
			response= "Usage Code Cannot be Null";
			return response;
		}
		if(param.get("usagename")!=null) {
			usagename=((String) param.get("usagename")).trim();
			m= p2.matcher(usagename);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Usage Name  are , . / ( ) - _";
				return response;
			}
				
			if(usagename.length()>255) {
				response= "Usage Name Cannot be more than 255 characters";
				return response;
			}
		}else {
			response= "Usage Name Cannot be Null";
			return response;
		}
		if(param.get("description")!=null) {
			description=((String) param.get("description")).trim();
			
			if(description.length()>250) {
				response= "Description Cannot be more than 250 characters";
				return response;
			}
		}
		else {
			response= "Description Cannot be Null";
			return response;
		}
		return response;
	}
}
