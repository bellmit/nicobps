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
				response= "occupancycodecharactererror";
				return response;
			}
			if(occupancycode.length()>10) {
				response= "occupancycodesizeerror";
				return response;
			}
		}
		else {
			response= "occupancycodenull";
			return response;
		}
		
		if(param.get("occupancyname")!=null) {
			occupancyname=((String) param.get("occupancyname")).trim();
			m= p.matcher(occupancyname);
			b = m.find();
			if(b) {
				response= "occupancynamecharactererror";
				return response;
			}
			if(occupancyname.length()>50) {
				response= "occupancynamesizeerror"; 
				return response;
			}
			
		}
		else
			response= "occupancynamenull";
		
		if(param.get("occupancyalias")!=null) {
			occupancyalias=((String) param.get("occupancyalias")).trim();
			m= p.matcher(occupancyalias);
			b = m.find();
			if(b) {
				response= "occupancyaliascharactererror";
				return response;
			}
			if(occupancyalias.length()>50) {
				response= "occupancyaliassizeerror";
				return response;
			}
		}
		else {
			response= "occupancyaliasnull";
			return response;
		}
			
		return response;
	}

}
