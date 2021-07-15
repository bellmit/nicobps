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
		String pattern="[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]"; 
		Pattern p = Pattern.compile(pattern);
		Pattern p1 = Pattern.compile("[^A-Za-z_ 0-9\\-]");
		String suboccupancycode="",suboccupancyname="",description="";
		
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
		}
		else {
			response= "Sub Occupancy Code Cannot Be Null";
			return response;
		}
			
		
		if(param.get("suboccupancyname")!=null) {
			suboccupancyname=((String) param.get("suboccupancyname")).trim();
			m= p.matcher(suboccupancyname);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Sub Occupancy Name  are , . / ( ) - _";
				return response;
			}
			if(suboccupancyname.length()>255) {
				response= "Sub Occupancy Name Cannot be more than 255 characters";
				return response;
			}
		}
		else
			response= "Sub Occupancy Name Cannot be Null";
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
