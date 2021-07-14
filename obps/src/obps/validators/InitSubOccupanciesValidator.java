package obps.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class InitSubOccupanciesValidator {
	public String validateInitSubOccupancies(Map<String, Object> param) {
		String response = "";
		Matcher m;
		boolean b=false;
		Pattern p = Pattern.compile("[^A-Za-z_ ]");
		Pattern p1 = Pattern.compile("[^A-Za-z_ 0-9]");
		String suboccupancycode="",suboccupancyname="",description="";
		
		if(param.get("suboccupancycode")!=null) {
			suboccupancycode=((String) param.get("suboccupancycode")).trim();
			m= p1.matcher(suboccupancycode);
			b = m.find();
			if(b) {
				response= "suboccupancycodecharactererror";
				return response;
			}
				
			if(suboccupancycode.length()>10) {
				response= "suboccupancycodesizeerror";
				return response;
			}
		}
		else {
			response= "suboccupancycodenull";
			return response;
		}
			
		
		if(param.get("suboccupancyname")!=null) {
			suboccupancyname=((String) param.get("suboccupancyname")).trim();
			m= p.matcher(suboccupancyname);
			b = m.find();
			if(b) {
				response= "suboccupancynamecharactererror"; 
				return response;
			}
			if(suboccupancycode.length()>255) {
				response= "suboccupancynamesizeerror";
				return response;
			}
		}
		else
			response= "suboccupancynamenull";
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
