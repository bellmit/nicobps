package obps.validators;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class InitOccupanciesValidator {
	public String validateInitOccupancies(Map<String, Object> param) {
		String response = "";
		Matcher m;
		boolean b=false;
		String pattern="[^A-Za-z_ 0-9\\'\\/\\.\\,\\-\\(\\)\\_]"; 
		Pattern p = Pattern.compile(pattern);
		Pattern p1 = Pattern.compile("[^A-Za-z_ 0-9\\-]");
		String occupancycode="",occupancyname="",occupancyalias="";
		if(param.get("occupancycode")!=null) {
			occupancycode=((String) param.get("occupancycode")).trim();
			m= p1.matcher(occupancycode);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Occupancy Code are  - _";
				return response;
			}
			if(occupancycode.length()>10) {
				response= "Occupancy Code Cannot be more than 10 characters";
				return response;
			}
		}
		else {
			response= "Occupancy Code Cannot Be Null";
			return response;
		}
		
		if(param.get("occupancyname")!=null) {
			occupancyname=((String) param.get("occupancyname")).trim();
			m= p.matcher(occupancyname);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Occupancy Name are  , . / ( ) - _";
				return response;
			}
			if(occupancyname.length()>50) {
				response= "Occupancy Name Cannot be more than 50 characters"; 
				return response;
			}
			
		}
		else
			response= "Occupancy Name Cannot be Null";
		
		if(param.get("occupancyalias")!=null) {
			occupancyalias=((String) param.get("occupancyalias")).trim();
			m= p.matcher(occupancyalias);
			b = m.find();
			if(b) {
				response= "Special Characters allowed in Occupancy Alias are  , . / ( ) - _";
				return response;
			}
			if(occupancyalias.length()>50) {
				response= "Occupancy Alias Cannot be more than 50 characters";
				return response;
			}
		}
		else {
			response= "Occupancy Alias Cannot be Null";
			return response;
		}
			
		return response;
	}

}
